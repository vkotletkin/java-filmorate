package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.FilmDefinition;

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
    public void testShouldCreateFilmCorrect() {
        film = FilmDefinition.filmFifthElement;

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertEquals(0, violations.size());
    }


}
