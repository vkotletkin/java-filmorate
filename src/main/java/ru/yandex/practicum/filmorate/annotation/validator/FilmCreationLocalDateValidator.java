package ru.yandex.practicum.filmorate.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotation.FilmCreationConstraint;

import java.time.LocalDate;

public class FilmCreationLocalDateValidator implements ConstraintValidator<FilmCreationConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        return localDate.isAfter(LocalDate.parse("1895-12-28"));
    }

}