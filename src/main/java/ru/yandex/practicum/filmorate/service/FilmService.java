package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import static ru.yandex.practicum.filmorate.exception.NotFoundException.notFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

    public Collection<Film> findAll() {
        return filmStorage.getFilms();
    }

    public Film createFilm(Film film) {
        film.setLikedIds(new HashSet<>());

        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        filmStorage.findFilmById(film.getId())
                .orElseThrow(notFoundException("Фильма с идентификатором: {0} - не существует.", film.getId()));

        return filmStorage.updateFilm(film);
    }

    public Map<String, String> deleteFilmById(Long id) {
        return filmStorage.deleteFilmById(id);
    }
}
