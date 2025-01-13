package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;
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
    public User createFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.createFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<Long> userFriends(@PathVariable Long id) {
        return userService.getUserFriends(id);
    }
}
