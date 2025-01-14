package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    public Collection<Film> getFilms() {
        return films.values();
    }

    public Film createFilm(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);

        return film;
    }

    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    public Map<String, String> deleteFilmById(Long filmId) {
        films.remove(filmId);
        return Map.of("description", String.format("Фильм с идентификатором: %d успешно удален", filmId));
    }

    public Optional<Film> findFilmById(Long id) {
        return Optional.ofNullable(films.get(id));
    }

    private long getNextId() {
        long currentMaxId = films.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}
