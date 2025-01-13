package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User deleteUser(User user) {
        return userStorage.deleteUser(user);
    }

    public Set<Long> createFriend(Long id, Long friendId) {
        User user = userStorage.getUsers()
                .stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Такого нету"));

        user.getFriendsIds().add(friendId);

        User friendUser = userStorage.getUsers()
                .stream()
                .filter(o -> o.getId().equals(friendId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Такого нету"));

        friendUser.getFriendsIds().add(id);

        return user.getFriendsIds();
    }

    public Set<User> getUserFriends(Long id) {
        User user = userStorage.getUsers()
                .stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Такого нету"));

        return userStorage.getUsers().stream()
                .filter(u -> user.getFriendsIds().contains(u.getId()))
                .collect(Collectors.toSet());

    }

    public User deleteFriend(Long id, Long friendId) {
        User user = userStorage.getUsers()
                .parallelStream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователя с идентификатором: %d - не существует", id)));

        User friendUser = userStorage.getUsers()
                .parallelStream()
                .filter(o -> o.getId().equals(friendId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователя с идентификатором: %d - не существует", id)));

        user.getFriendsIds().remove(friendId);
        friendUser.getFriendsIds().remove(id);

        return user;

    }

    public Set<User> getCommonFriends(Long id, Long otherId) {
        User user = findUser(id);
        User otherUser = findUser(otherId);

        Set<Long> idsIntersection = findIdsIntersection(user.getFriendsIds(), otherUser.getFriendsIds());

        return userStorage.getUsers().stream()
                .filter(u -> idsIntersection.contains(u.getId()))
                .collect(Collectors.toSet());
    }

    private User findUser(Long id) {
        return userStorage.getUsers()
                .parallelStream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователя с идентификатором: %d - не существует", id)));
    }

    private Set<Long> findIdsIntersection(Set<Long> firstIds, Set<Long> secondIds) {
        Set<Long> intersection = new HashSet<>(firstIds);
        intersection.retainAll(secondIds);
        return intersection;
    }
}
