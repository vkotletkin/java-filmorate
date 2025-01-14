package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

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

        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) {
        filmStorage.findById(film.getId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("Произошла ошибка добавления фильма. Полученного идентификатора %s не существует в хранилище!",
                                film.getId())));

        return filmStorage.update(film);
    }

    public Map<String, String> deleteFilmById(Film film) {
        return filmStorage.deleteById(film.getId());
    }
}
