package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Запрошен возврат списка всех фильмов");
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (isNotCorrectReleaseDate(film)) {
            throw new ValidationException("Дата фильма не корректна!");
        }

        film.setId(getNextId());
        log.info("Для создаваемого фильма установлен идентификатор: {}", film.getId());

        films.put(film.getId(), film);
        log.info("Фильм с идентификатором: {} успешно добавлен в хранилище", film.getId());

        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (isNotCorrectReleaseDate(film)) {
            throw new ValidationException("Дата фильма не корректна!");
        }

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм с идентификатором: {} успешно обновлен", film.getId());
            return film;
        } else {
            throw new ValidationException(String.format("Фильма с идентификатором: %s не существует!", film.getId()));
        }
    }

    private boolean isNotCorrectReleaseDate(Film film) {
        return !film.getReleaseDate().isAfter(LocalDate.parse("1895-12-28"));
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
