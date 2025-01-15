package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;

import static ru.yandex.practicum.filmorate.exception.NotFoundException.notFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        return filmStorage.getFilms();
    }

    public Film createFilm(Film film) {
        film.setLikedIds(new HashSet<>());

        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {

        filmStorage.findFilmById(film.getId())
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", film.getId()));

        if (film.getLikedIds() == null) {
            film.setLikedIds(new HashSet<>());
        }

        return filmStorage.updateFilm(film);
    }

    public Map<String, String> deleteFilmById(Long id) {
        return filmStorage.deleteFilmById(id);
    }

    public Film createLike(Long id, Long userId) {

        Film film = filmStorage.findFilmById(id)
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", id));

        userStorage.findUserById(userId)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", userId));

        film.getLikedIds().add(userId);

        return film;
    }

    public Map<String, String> deleteLike(Long id, Long userId) {
        Film film = filmStorage.findFilmById(id)
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", id));

        userStorage.findUserById(userId)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", userId));

        film.getLikedIds().remove(userId);

        return Map.of("description",
                String.format("Фильм с идентификатором: " +
                        "%d больше не содержит лайк от пользователя с идентификатором: %d", id, userId));
    }

    public Collection<Film> findPopularFilms(Long count) {
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparing(u -> u.getLikedIds().size()))
                .skip(Math.max(0, filmStorage.getFilms().size() - count))
                .toList().reversed();
    }
}
