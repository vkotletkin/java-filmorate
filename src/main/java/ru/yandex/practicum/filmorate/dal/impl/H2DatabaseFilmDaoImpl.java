package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.FilmDao;
import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository("h2Film")
@RequiredArgsConstructor
public class H2DatabaseFilmDaoImpl implements FilmDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Collection<Film> getFilms() {
        return List.of();
    }

    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public Map<String, String> deleteFilmById(Long id) {
        return Map.of();
    }

    @Override
    public Optional<Film> findFilmById(Long id) {
        return Optional.empty();
    }
}
