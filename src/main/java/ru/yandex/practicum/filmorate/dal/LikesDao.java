package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.entity.Like;

import java.util.List;

public interface LikesDao {
    List<Like> findAll();

    List<Like> findLikesByFilmId(Long id);

    List<Like> findLikesByUserId(Long id);

    List<Like> findLikesByFilmIdAndUserId(Long id, Long userId);

    Like addLike(Like like);

    Like deleteLike(Like like);
}
