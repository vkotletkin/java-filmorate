package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    public Collection<Film> getFilms() {
        return films.values();
    }

    public Film createFilm(Film film) {
        film.setId(getNextId());
        log.info("Для создаваемого фильма установлен идентификатор: {}", film.getId());

        films.put(film.getId(), film);
        log.info("Фильм с идентификатором: {} успешно добавлен в хранилище", film.getId());

        return film;
    }

    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм с идентификатором: {} успешно обновлен", film.getId());
            return film;
        } else {
            log.warn("Произошла ошибка добавления фильма. Полученного идентификатора {} не существует в хранилище!",
                    film.getId());

            throw new NotFoundException(
                    String.format("Произошла ошибка добавления фильма. Полученного идентификатора %s не существует в хранилище!",
                            film.getId()));
        }
    }

    private long getNextId() {
        long currentMaxId = films.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}
