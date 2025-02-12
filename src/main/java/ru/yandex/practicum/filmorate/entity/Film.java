package ru.yandex.practicum.filmorate.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.annotation.FilmCreationConstraint;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Film {
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

    Long mpa;
    Long genre;
}
