package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.FilmDao;
import ru.yandex.practicum.filmorate.dal.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository("h2Film")
@RequiredArgsConstructor
public class H2DatabaseFilmDaoImpl implements FilmDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Collection<Film> findAll() {
        String query = "SELECT film_id, name, description, release_date, duration, association_rating FROM films";
        return jdbcTemplate.query(query, new FilmRowMapper());
    }

    @Override
    public Film createFilm(Film film) {
        String query = """
                INSERT INTO films (name, description, release_date, duration, association_rating)
                VALUES (:name, :description, :release_date, :duration, :association_rating);
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("release_date", film.getReleaseDate());
        params.addValue("duration", film.getDuration());
        params.addValue("association_rating", film.getMpa());
        jdbcTemplate.update(query, params);

        return findFilmByName(film.getName()).stream().findFirst().orElse(null);
    }

    @Override
    public Film updateFilm(Film film) {
        String query = """
                UPDATE films
                SET film_id            = :film_id,
                    name               = :name,
                    description        = :description,
                    release_date       = :release_date,
                    duration           = :duration,
                    association_rating = :association_rating
                WHERE film_id = :film_id;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("film_id", film.getId());
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("release_date", film.getReleaseDate());
        params.addValue("duration", film.getDuration());
        params.addValue("association_rating", film.getMpa());
        jdbcTemplate.update(query, params);

        return findFilmById(film.getId()).stream().findFirst().orElse(null);
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
    public List<Film> findFilmById(Long id) {
        String query = """
                SELECT film_id, name, description, release_date, duration, association_rating
                FROM films
                WHERE film_id = :film_id;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("film_id", id);

        return jdbcTemplate.query(query, params, new FilmRowMapper());
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
        String query = """
                SELECT film_id, name, description, release_date, duration, association_rating
                FROM films
                WHERE name = :name;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        return jdbcTemplate.query(query, params, new FilmRowMapper());
    }
}
