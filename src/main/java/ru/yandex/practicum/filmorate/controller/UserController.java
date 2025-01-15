package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Map;
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

    @DeleteMapping
    public Map<String, String> delete(@RequestParam Long id) {
        log.info("Выполняется удаление пользователя по идентификатору");
        return userService.deleteUserById(id);
    }

    @PutMapping("/{id}/friends/{friend-id}")
    public User createFriend(@PathVariable(name = "id") Long id,
                             @PathVariable(name = "friend-id") Long friendId) {
        return userService.createFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> userFriends(@PathVariable(name = "id") Long id) {
        return userService.getUserFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friend-id}")
    public Map<String, String> deleteFriend(@PathVariable(name = "id") Long id,
                                            @PathVariable(name = "friend-id") Long friendId) {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{other-id}")
    public Set<User> getCommonFriends(@PathVariable(name = "id") Long id,
                                      @PathVariable(name = "other-id") Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
