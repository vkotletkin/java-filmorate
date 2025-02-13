package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.entity.Genre;

import java.util.List;

public interface GenreDao {
    void createGenreBatch(Long filmId, final List<GenreDto> genres);
    List<Genre> findAll();
}
