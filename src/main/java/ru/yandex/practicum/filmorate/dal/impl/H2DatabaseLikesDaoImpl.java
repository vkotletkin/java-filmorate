package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.LikesDao;
import ru.yandex.practicum.filmorate.dal.mapper.LikeRowMapper;
import ru.yandex.practicum.filmorate.entity.Like;

import java.util.List;

import static ru.yandex.practicum.filmorate.dal.impl.query.FilmQuery.QUERY_FIND_ALL;
import static ru.yandex.practicum.filmorate.dal.impl.query.LikeQuery.QUERY_ADD_LIKE;
import static ru.yandex.practicum.filmorate.dal.impl.query.LikeQuery.QUERY_DELETE_LIKE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseLikesDaoImpl implements LikesDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Like> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new LikeRowMapper());
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        jdbcTemplate.update(QUERY_ADD_LIKE, filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        jdbcTemplate.update(QUERY_DELETE_LIKE, filmId, userId);
    }
}
