package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FilmDao {

    Collection<Film> findAll();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Map<String, String> deleteFilmById(Long id);

    List<Film> findFilmById(Long id);

    List<Film> findFilmByLogin(Long id);

    List<Film> findFilmByName(Long id);

    List<Film> findFilmByName(String name);
}
