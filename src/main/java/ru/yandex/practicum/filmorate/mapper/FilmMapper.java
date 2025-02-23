package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.entity.Film;

import java.util.List;

public final class FilmMapper {
    public static Film mapToFilm(FilmDto filmDto) {
        return Film.builder()
                .id(filmDto.getId())
                .name(filmDto.getName())
                .description(filmDto.getDescription())
                .duration(filmDto.getDuration())
                .releaseDate(filmDto.getReleaseDate())
                .mpa(filmDto.getMpa().getId())
                .build();
    }

    public static FilmDto mapToDto(Film film, MpaDto mpaDto, List<GenreDto> genreDto) {
        FilmDto filmDto = mapToDto(film);
        filmDto.setMpa(mpaDto);
        filmDto.setGenres(genreDto);
        return filmDto;
    }

    public static FilmDto mapToDto(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDescription(film.getDescription());
        filmDto.setDuration(film.getDuration());
        filmDto.setReleaseDate(film.getReleaseDate());
        return filmDto;
    }
}
