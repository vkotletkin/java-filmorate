package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Map<String, String> deleteFilmById(Long id);

    Optional<Film> findFilmById(Long id);
}
