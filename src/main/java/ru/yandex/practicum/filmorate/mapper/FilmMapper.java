package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.entity.Film;

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
}
