package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.entity.Film;
import ru.yandex.practicum.filmorate.utils.FilmDefinition;

import java.time.LocalDate;
import java.util.Set;

public class FilmControllerTests {

    Validator validator;
    Film film;

    @BeforeEach
    public void createValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidData() {
        film = FilmDefinition.filmFifthElement;

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void testValidName() {
        film = Film.builder()
                .id(FilmDefinition.filmFifthElement.getId())
                .description(FilmDefinition.filmFifthElement.getDescription())
                .releaseDate(FilmDefinition.filmFifthElement.getReleaseDate())
                .duration(FilmDefinition.filmFifthElement.getDuration())
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    public void testValidDescription() {
        film = Film.builder()
                .id(FilmDefinition.filmFifthElement.getId())
                .name(FilmDefinition.filmFifthElement.getName())
                .releaseDate(FilmDefinition.filmFifthElement.getReleaseDate())
                .duration(FilmDefinition.filmFifthElement.getDuration())
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    public void testValidDate() {
        film = Film.builder()
                .id(FilmDefinition.filmFifthElement.getId())
                .name(FilmDefinition.filmFifthElement.getName())
                .releaseDate(LocalDate.parse("1500-01-01"))
                .description(FilmDefinition.filmFifthElement.getDescription())
                .duration(FilmDefinition.filmFifthElement.getDuration())
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    public void testValidDuration() {
        film = Film.builder()
                .id(FilmDefinition.filmFifthElement.getId())
                .name(FilmDefinition.filmFifthElement.getName())
                .description(FilmDefinition.filmFifthElement.getDescription())
                .releaseDate(FilmDefinition.filmFifthElement.getReleaseDate())
                .duration(-200L)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertEquals(1, violations.size());
    }
}
