package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.entity.Like;

import java.util.List;

public interface LikesDao {
    List<Like> findAll();

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}
