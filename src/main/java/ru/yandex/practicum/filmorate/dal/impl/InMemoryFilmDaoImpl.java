package ru.yandex.practicum.filmorate.dal.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.FilmDao;
import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@Primary
public class InMemoryFilmDaoImpl implements FilmDao {
    private final Map<Long, Film> films = new HashMap<>();

    public Collection<Film> getFilms() {
        log.info("Выполняется получение списка всех фильмов.");
        return films.values();
    }

    public Film createFilm(Film film) {
        film.setId(getNextId());

        films.put(film.getId(), film);
        log.info("Добавлен новый фильм с идентификатором {}.", film.getId());

        return film;
    }

    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        log.info("Фильм с идентификатором {} успешно обновлен.", film.getId());
        return film;
    }

    public Map<String, String> deleteFilmById(Long filmId) {
        films.remove(filmId);
        log.info("Фильм с идентификатором {} успешно удален.", filmId);
        return Map.of("description", String.format("Фильм с идентификатором: %d успешно удален.", filmId));
    }

    public Optional<Film> findFilmById(Long id) {
        log.info("Выполняется получение фильма с идентификатором: {}.", id);
        return Optional.ofNullable(films.get(id));
    }

    private long getNextId() {
        long currentMaxId = films.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}
