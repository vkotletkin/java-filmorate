package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.annotation.validator.FilmCreationLocalDateValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = FilmCreationLocalDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FilmCreationConstraint {
    String message() default "The date of the film's creation is incorrect. The date must be after 1895-12-28";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
