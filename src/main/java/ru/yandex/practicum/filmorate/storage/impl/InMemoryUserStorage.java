package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public User createUser(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);

        return user;
    }

    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public Map<String, String> deleteUserById(Long id) {
        users.remove(id);
        return Map.of("description", String.format("Фильм с идентификатором: %d успешно удален", id));
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    private long getNextId() {
        long currentMaxId = users.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }

}
