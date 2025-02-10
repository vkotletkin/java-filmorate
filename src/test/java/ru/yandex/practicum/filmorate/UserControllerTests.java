package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.entity.User;
import ru.yandex.practicum.filmorate.utils.UserDefinition;

import java.time.LocalDate;
import java.util.Set;

public class UserControllerTests {

    Validator validator;
    User user;

    @BeforeEach
    public void createValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidData() {
        user = UserDefinition.userLucBesson;

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void testValidLogin() {
        user = User.builder()
                .id(1L)
                .email(UserDefinition.userLucBesson.getEmail())
                .birthday(UserDefinition.userLucBesson.getBirthday())
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertEquals(1, violations.size());

    }

    @Test
    public void testValidEmail() {
        user = User.builder()
                .id(1L)
                .login(UserDefinition.userLucBesson.getLogin())
                .email("zhora-test-mail")
                .birthday(UserDefinition.userLucBesson.getBirthday())
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    public void testValidBirthday() {
        user = User.builder()
                .id(1L)
                .login(UserDefinition.userLucBesson.getLogin())
                .email(UserDefinition.userLucBesson.getEmail())
                .birthday(LocalDate.parse("2027-01-01"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertEquals(1, violations.size());
    }

}
