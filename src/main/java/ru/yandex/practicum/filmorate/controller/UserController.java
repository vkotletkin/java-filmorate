package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        log.info("Запрошен возврат списка всех пользователей");
        return userService.findAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Выполняется создание нового пользователя");
        return userService.createUser(user);

    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Выполняется обновление пользователя.");
        return userService.updateUser(user);
    }

    // TODO: delete crud

    @PutMapping("/{id}/friends/{friendId}")
    public Set<Long> createFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.createFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> userFriends(@PathVariable Long id) {
        return userService.getUserFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
