package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.entity.Film;

import java.time.LocalDate;

public class FilmDefinition {
    public static Film filmFifthElement = Film.builder()
            .id(1L)
            .name("The Fifth Element")
            .description("A fantastic comedy thriller about saving the Earth")
            .releaseDate(LocalDate.parse("1997-05-02"))
            .duration(7560L)
            .build();
}
