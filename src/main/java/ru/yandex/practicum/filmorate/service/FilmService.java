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
import ru.yandex.practicum.filmorate.entity.Mpa;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;

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
        List<GenreDto> genres = filmDto.getGenres();
        mpaDao.findById(mpaDto.getId()).orElseThrow(notFoundException("Такого MPA не существует."));

        Film film = filmDao.createFilm(FilmMapper.mapToFilm(filmDto));
        if (genres != null && !genres.isEmpty()) {
            getExistedGenresIds(genres, film.getId());
        }
        filmDto.setId(film.getId());
        return filmDto;
    }

    private void getExistedGenresIds(List<GenreDto> genres, Long id) {
        List<Long> genresRealIds = genreDao.findAll().stream().map(Genre::getId).toList();
        List<Long> genresInputIds = genres.stream().map(GenreDto::getId).distinct().toList();
        Set<Long> interIds = genresInputIds.stream().filter(genresRealIds::contains).collect(Collectors.toSet());
        if (interIds.size() != genresInputIds.size()) {
            throw new NotFoundException("В списке жанров присутствуют некорректные жанры.");
        }
        genreDao.createGenreBatch(id, genres);
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
        userDao.findUserById(userId).orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", userId));
        likesDao.addLike(id, userId);
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
        return filmDao.findPopularFilms(count);
    }

    public List<GenreDto> findGenres() {
        return genreDao.findAll().stream().map(GenreMapper::mapToDto).collect(Collectors.toList());
    }

    public GenreDto findGenreById(Long id) {
        Genre genre = genreDao.findById(id).orElseThrow(notFoundException("Жанр с идентификатором {0} - не существует", id));
        return GenreMapper.mapToDto(genre);
    }

    public List<MpaDto> findAllMpa() {
        return mpaDao.findAll().stream().map(MpaMapper::mapToDto).collect(Collectors.toList());
    }

    public MpaDto findMpaById(Long id) {
        Mpa mpa = mpaDao.findById(id).orElseThrow(notFoundException("MPA с идентификатором {0} - не существует", id));
        return MpaMapper.mapToDto(mpa);
    }

    public FilmDto getFilmFull(Long id) {
        Film film = filmDao.findFilmById(id).orElseThrow(notFoundException("Фильм с идентификатором {0} - не существует", id));
        MpaDto mpaDto = MpaMapper.mapToDto(mpaDao.findById(film.getMpa()).orElseThrow(
                notFoundException("MPA с идентификатором {0} - не существует", id)));
        List<GenreDto> genreDto = genreDao.findGenresByFilmId(id).stream().map(GenreMapper::mapToDto).toList();
        return FilmMapper.mapToDto(film, mpaDto, genreDto);
    }
}
