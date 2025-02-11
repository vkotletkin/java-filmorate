package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.LikesDao;
import ru.yandex.practicum.filmorate.entity.Like;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseLikesDaoImpl implements LikesDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Like> findAll() {
        return List.of();
    }

    @Override
    public List<Like> findLikesByFilmId(Long id) {
        return List.of();
    }

    @Override
    public List<Like> findLikesByUserId(Long id) {
        return List.of();
    }

    @Override
    public List<Like> findLikesByFilmIdAndUserId(Long id, Long userId) {
        return List.of();
    }

    @Override
    public Like addLike(Like like) {
        return null;
    }

    @Override
    public Like deleteLike(Like like) {
        return null;
    }
}
