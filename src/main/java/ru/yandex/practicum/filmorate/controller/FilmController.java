package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

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
        film.setId(getNextId());
        log.info("Для создаваемого фильма установлен идентификатор: {}", film.getId());

        films.put(film.getId(), film);
        log.info("Фильм с идентификатором: {} успешно добавлен в хранилище", film.getId());

        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм с идентификатором: {} успешно обновлен", film.getId());
            return film;
        } else {
            log.warn("Произошла ошибка добавления фильма. Полученного идентификатора {} не существует в хранилище!", film.getId());

            throw new ValidationException(
                    String.format("Произошла ошибка добавления фильма. Полученного идентификатора %s не существует в хранилище!", film.getId()));
        }
    }

    private long getNextId() {
        long currentMaxId = films.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}
