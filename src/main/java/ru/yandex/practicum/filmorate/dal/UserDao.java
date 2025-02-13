package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {
    Collection<User> findAll();

    User createUser(User user);

    User updateUser(User user);

    Map<String, String> deleteUserById(Long id);

    Optional<User> findUserById(Long id);
}
