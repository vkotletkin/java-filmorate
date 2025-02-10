package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FilmDao {

    Collection<Film> getFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Map<String, String> deleteFilmById(Long id);

    Optional<Film> findFilmById(Long id);
}
