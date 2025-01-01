package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

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
    public User create(@Valid @RequestBody User user) {
        log.info("Проверка создаваемого пользователя с логином: {} на пустое имя", user.getLogin());
        user = fillEmptyNameWithLogin(user);

        user.setId(getNextId());
        log.info("Для создаваемого пользователя установлен идентификатор: {}", user.getId());

        users.put(user.getId(), user);
        log.info("Пользователь с идентификатором: {} успешно добавлен в хранилище", user.getId());

        return user;

    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь с идентификатором: {} успешно обновлен", user.getId());
            return user;
        } else {
            throw new ValidationException(String.format("Пользователя с идентификатором: %s не существует!", user.getId()));
        }
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
