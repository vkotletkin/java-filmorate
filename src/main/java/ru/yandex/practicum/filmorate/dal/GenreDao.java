package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.entity.FilmGenre;
import ru.yandex.practicum.filmorate.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    void createGenreBatch(Long filmId, final List<GenreDto> genres);

    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    List<Genre> findGenresByFilmId(Long filmId);
}
