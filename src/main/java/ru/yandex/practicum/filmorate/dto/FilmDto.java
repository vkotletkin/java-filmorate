package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.annotation.FilmCreationConstraint;

import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmDto {

    Long id;

    @NotBlank
    String name;

    @NotBlank
    @Size(max = 200)
    String description;

    @NotNull
    @FilmCreationConstraint
    LocalDate releaseDate;

    @NotNull
    @Min(1)
    Long duration;

    MpaDto mpa;

    List<GenreDto> genres;
}
