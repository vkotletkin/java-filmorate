package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmDao;
import ru.yandex.practicum.filmorate.dal.LikesDao;
import ru.yandex.practicum.filmorate.dal.UserDao;
import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Collection;
import java.util.Map;

import static ru.yandex.practicum.filmorate.exception.NotFoundException.notFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmDao filmDao;

    private final UserDao userDao;

    private final LikesDao likesDao;

    public Collection<Film> findAll() {
        return filmDao.findAll();
    }

    public Film createFilm(Film film) {
        return filmDao.createFilm(film);
    }

    public Film updateFilm(Film film) {
        filmDao.findFilmById(film.getId()).stream().findFirst()
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", film.getId()));
        return filmDao.updateFilm(film);
    }

    public Map<String, String> deleteFilmById(Long id) {
        return filmDao.deleteFilmById(id);
    }

    public Film createLike(Long id, Long userId) {

        Film film = filmDao.findFilmById(id).stream().findFirst()
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", id));

//        userDao.findUserById(userId).stream().findFirst()
//                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", userId));

        likesDao.addLike(id, userId);

        return film;
    }

    public Map<String, String> deleteLike(Long id, Long userId) {
        Film film = filmDao.findFilmById(id).stream().findFirst()
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", id));

//        userDao.findUserById(userId).stream().findFirst()
//                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", userId));

        likesDao.deleteLike(id, userId);

        return Map.of("description",
                String.format("Фильм с идентификатором: " +
                        "%d больше не содержит лайк от пользователя с идентификатором: %d", id, userId));
    }

    public Collection<Film> findPopularFilms(Long count) {
//        return filmDao.findAll().stream()
//                .sorted(Comparator.comparing(u -> u.getLikedIds().size()))
//                .skip(Math.max(0, filmDao.getFilms().size() - count))
//                .toList().reversed();
        return filmDao.findAll();
        // TODO: REFACTOR!!!!
    }

}
