package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.FilmDao;
import ru.yandex.practicum.filmorate.dal.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.entity.Film;

import java.sql.PreparedStatement;
import java.util.*;

@Slf4j
@Repository("h2Film")
@RequiredArgsConstructor
public class H2DatabaseFilmDaoImpl implements FilmDao {

    private static final String QUERY_FIND_ALL = """
            SELECT film_id, name, description, release_date, duration, mpa
            FROM films;
            """;
    private static final String QUERY_CREATE_FILM = """
            INSERT INTO films (name, description, release_date, duration, mpa)
            VALUES (?, ?, ?, ?, ?);
            """;

    private static final String QUERY_UPDATE_FILM = """
            UPDATE films
            SET name               = ?,
                description        = ?,
                release_date       = ?,
                duration           = ?,
                mpa = ?
            WHERE film_id = ?;
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new FilmRowMapper());
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
        String query = """
                DELETE FROM films
                WHERE film_id = :film_id;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("film_id", id);
        jdbcTemplate.update(query, params);

        return Map.of("description", String.format("Фильм с идентификатором: %d успешно удален.", id));
    }

    @Override
    public Optional<Film> findFilmById(Long id) {
        String query = """
                SELECT film_id, name, description, release_date, duration, mpa
                FROM films
                WHERE film_id = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new FilmRowMapper(), id));
    }

    @Override
    public List<Film> findFilmByLogin(Long id) {
        return List.of();
    }

    @Override
    public List<Film> findFilmByName(Long id) {
        return List.of();
    }

    @Override
    public List<Film> findFilmByName(String name) {
        return List.of();
    }
}
