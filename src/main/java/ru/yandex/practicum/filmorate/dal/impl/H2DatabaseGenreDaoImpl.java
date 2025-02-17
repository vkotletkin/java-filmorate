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
import java.util.Optional;

import static ru.yandex.practicum.filmorate.dal.impl.query.GenreQuery.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseGenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public void createGenreBatch(Long filmId, final List<GenreDto> genres) {
        jdbcTemplate.batchUpdate(QUERY_BATCH_INSERT,
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
        return jdbcTemplate.query(QUERY_FIND_ALL, new GenreRowMapper());
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, new GenreRowMapper(), id));
    }

    @Override
    public List<Genre> findGenresByFilmId(Long filmId) {
        return jdbcTemplate.query(QUERY_FOR_GET_FILM_GENRES, new GenreRowMapper(), filmId);
    }
}
