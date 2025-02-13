package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.GenreDao;
import ru.yandex.practicum.filmorate.dal.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.entity.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseGenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public void createGenreBatch(Long filmId, final List<GenreDto> genres) {
        jdbcTemplate.batchUpdate("INSERT INTO FILMS_GENRES (FILM_ID, GENRE_ID) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, genres.get(i).getId());
                    }

                    public int getBatchSize() {
                        return genres.size();
                    }

                });
    }

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query("SELECT GENRE_ID, GENRE_NAME FROM GENRES;", new GenreRowMapper());
    }
}
