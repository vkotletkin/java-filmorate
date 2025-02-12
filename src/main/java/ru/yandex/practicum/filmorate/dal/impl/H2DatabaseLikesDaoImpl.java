package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.LikesDao;
import ru.yandex.practicum.filmorate.dal.mapper.LikeRowMapper;
import ru.yandex.practicum.filmorate.entity.Like;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseLikesDaoImpl implements LikesDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Like> findAll() {
        String query = "SELECT like_id, film_id, user_id FROM film_likes";
        return jdbcTemplate.query(query, new LikeRowMapper());
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String query = "INSERT INTO film_likes (film_id, user_id) VALUES (:film_id, :user_id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("film_id", filmId);
        params.addValue("user_id", userId);
        jdbcTemplate.update(query, params);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        String query = "DELETE FROM film_likes WHERE film_id = :film_id AND user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("film_id", filmId);
        params.addValue("user_id", userId);
        jdbcTemplate.update(query, params);
    }
}
