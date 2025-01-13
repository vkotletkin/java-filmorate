package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public User createUser(User user) {
        log.info("Проверка создаваемого пользователя с логином: {} на пустое имя", user.getLogin());
        User newUser = fillEmptyNameWithLogin(user);

        newUser.setId(getNextId());

        if (user.getFriendsIds() == null) {
            newUser.setFriendsIds(new HashSet<>());
        }

        log.info("Для создаваемого пользователя установлен идентификатор: {}", user.getId());

        users.put(newUser.getId(), newUser);
        log.info("Пользователь с идентификатором: {} успешно добавлен в хранилище", user.getId());

        return user;
    }

    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь с идентификатором: {} успешно обновлен", user.getId());
            return user;
        } else {
            throw new NotFoundException(String.format("Пользователя с идентификатором: %s не существует!",
                    user.getId()));
        }
    }

    public User deleteUser(User user) {
        users.remove(user.getId());
        return user;
    }

    private long getNextId() {
        long currentMaxId = users.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }

    private User fillEmptyNameWithLogin(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }

}
