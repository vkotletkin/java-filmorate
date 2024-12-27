package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        if (isNotPassedValidation(film)) {
            throw new ValidationException("Название фильма не может быть пустым!");
        }

        film.setId(getNextId());
        films.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (isNotPassedValidation(film)) {
            throw new ValidationException("Название фильма не может быть пустым!");
        }

        films.put(film.getId(), film);
        return film;
    }

    private boolean isNotPassedValidation(Film film) {
        return !(isCorrectFilmName(film) && isCorrectDescription(film) && isCorrectReleaseDate(film) && isCorrectDuration(film));
    }

    private boolean isCorrectFilmName(Film film) {
        return film.getName() == null || film.getName().isBlank();
    }

    private boolean isCorrectDescription(Film film) {
        return film.getDescription().length() <= 200;
    }

    private boolean isCorrectReleaseDate(Film film) {
        return film.getReleaseDate().isAfter(LocalDate.parse("1895-12-28"));
    }

    private boolean isCorrectDuration(Film film) {
        return film.getDuration().isPositive();
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
