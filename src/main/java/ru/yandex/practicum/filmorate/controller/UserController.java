package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.info("Запрошен возврат списка всех пользователей");
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        if (isNotPassedValidation(user)) {
            throw new ValidationException("Передаваемые данные не соответствую определенным критериям!");
        }

        log.info("Проверка создаваемого пользователя с логином: {} на пустое имя", user.getLogin());
        user = fillEmptyNameWithLogin(user);

        user.setId(getNextId());
        log.info("Для создаваемого пользователя установлен идентификатор: {}", user.getId());

        users.put(user.getId(), user);
        log.info("Пользователь с идентификатором: {} успешно добавлен в хранилище", user.getId());

        return user;

    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (isNotPassedValidation(user)) {
            throw new ValidationException("Передаваемые данные не соответствую определенным критериям!");
        }

        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь с идентификатором: {} успешно обновлен", user.getId());
            return user;
        } else {
            throw new ValidationException("Передаваемые данные не соответствую определенным критериям!");
        }
    }

    public boolean isNotPassedValidation(User user) {
        return !(isEmailCorrected(user) && isLoginCorrected(user) && isDateOfBirthCorrect(user));
    }

    public boolean isEmailCorrected(User user) {
        return !user.getEmail().isBlank() && user.getEmail() != null && user.getEmail().contains("@");
    }

    public boolean isLoginCorrected(User user) {
        return !user.getLogin().isBlank() && user.getLogin() != null && !user.getLogin().contains(" ");
    }

    public boolean isDateOfBirthCorrect(User user) {
        return user.getBirthday().isBefore(LocalDate.now());
    }

    public User fillEmptyNameWithLogin(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
