package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.entity.Film;
import ru.yandex.practicum.filmorate.entity.Genre;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.exception.NotFoundException.notFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmDao filmDao;

    private final LikesDao likesDao;
    private final GenreDao genreDao;
    private final UserDao userDao;
    private final MpaDao mpaDao;

    public Collection<Film> findAll() {
        return filmDao.findAll();
    }

    public FilmDto createFilm(FilmDto filmDto) {
        MpaDto mpaDto = filmDto.getMpa();
        mpaDao.findById(mpaDto.getId()).orElseThrow(notFoundException("mpa not exists"));
        List<GenreDto> genres = filmDto.getGenres();
        List<Long> genreList = genreDao.findAll().stream().map(Genre::getId).toList();
        List<Long> genreListDto = genres.stream().map(GenreDto::getId).toList();
        Set<Long> result = genreListDto.stream().filter(genreList::contains).collect(Collectors.toSet());
        if (result.size() != genreListDto.size()) {
            throw new NotFoundException("genre not exists");
        }

        Film film = filmDao.createFilm(FilmMapper.mapToFilm(filmDto));
        genreDao.createGenreBatch(film.getId(), genres);
        filmDto.setId(film.getId());
        return filmDto;

    }

    public Film updateFilm(FilmDto filmDto) {
        Film film = FilmMapper.mapToFilm(filmDto);
        filmDao.findFilmById(film.getId())
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", film.getId()));

        return filmDao.updateFilm(film);
    }

    public Map<String, String> deleteFilmById(Long id) {
        return filmDao.deleteFilmById(id);
    }

    public Film createLike(Long id, Long userId) {

        Film film = filmDao.findFilmById(id)
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", id));

        userDao.findUserById(userId).orElseThrow(notFoundException("user not exists"));


        likesDao.addLike(id, userId);
        // TEST TODO: return map like add
        return film;
    }

    public Map<String, String> deleteLike(Long id, Long userId) {
        filmDao.findFilmById(id).stream().findFirst()
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", id));

        userDao.findUserById(userId)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", userId));

        likesDao.deleteLike(id, userId);

        return Map.of("description",
                String.format("Фильм с идентификатором: " +
                        "%d больше не содержит лайк от пользователя с идентификатором: %d", id, userId));
    }

    public Collection<Film> findPopularFilms(Long count) {
        // TODO: set dto
        return filmDao.findPopularFilms(count);
    }

}
