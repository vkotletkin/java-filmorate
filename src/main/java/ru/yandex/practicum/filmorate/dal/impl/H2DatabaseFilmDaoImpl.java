package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.FilmDao;
import ru.yandex.practicum.filmorate.dal.impl.query.FilmQuery;
import ru.yandex.practicum.filmorate.dal.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.entity.Film;

import java.sql.PreparedStatement;
import java.util.*;

import static ru.yandex.practicum.filmorate.dal.impl.query.FilmQuery.*;

@Slf4j
@Repository("h2Film")
@RequiredArgsConstructor
public class H2DatabaseFilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(FilmQuery.QUERY_FIND_ALL, new FilmRowMapper());
    }

    @Override
    public Film createFilm(Film film) {
        long key = insertMessage(film);
        film.setId(key);
        return film;
    }

    private long insertMessage(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(QUERY_CREATE_FILM, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setString(3, String.valueOf(film.getReleaseDate()));
            ps.setString(4, film.getDuration().toString());
            ps.setString(5, film.getMpa().toString());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private long updateMessage(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(QUERY_UPDATE_FILM,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setString(3, String.valueOf(film.getReleaseDate()));
            ps.setString(4, film.getDuration().toString());
            ps.setString(5, film.getMpa().toString());
            ps.setLong(6, film.getId());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Film updateFilm(Film film) {
        long key = updateMessage(film);
        film.setId(key);
        return film;
    }

    @Override
    public Map<String, String> deleteFilmById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("film_id", id);
        jdbcTemplate.update(QUERY_DELETE_FILM_BY_ID, params);

        return Map.of("description", String.format("Фильм с идентификатором: %d успешно удален.", id));
    }

    @Override
    public Optional<Film> findFilmById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY_FIND_FILM_BY_ID, new FilmRowMapper(), id));
    }

    @Override
    public List<Film> findPopularFilms(Long count) {
        return jdbcTemplate.query(QUERY_FIND_POPULAR_FILMS, new FilmRowMapper(), count);
    }
}
