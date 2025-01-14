package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        user.setFriendsIds(new HashSet<>());
        User userProcessed = fillEmptyNameWithLogin(user);

        return userStorage.createUser(userProcessed);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public Map<String, String> deleteUserById(Long id) {
        return userStorage.deleteUserById(id);
    }

    public Set<Long> createFriend(Long id, Long friendId) {
        User user = userStorage.findUserById(id)
                .orElseThrow(notFoundException("Пользователя с идентификатором: {0} - не существует.", id));

        User friendUser = userStorage.findUserById(friendId)
                .orElseThrow(notFoundException("Пользователя с идентификатором: {0} - не существует.", friendId));

        user.getFriendsIds().add(friendId);
        friendUser.getFriendsIds().add(id);

        return user.getFriendsIds();
    }

    public Set<User> getUserFriends(Long id) {
        userStorage.findUserById(id)
                .orElseThrow(notFoundException("Пользователя с идентификатором: {0} - не существует.", id));

        return userStorage.getUsers().stream()
                .filter(u -> u.getFriendsIds().contains(id))
                .collect(Collectors.toSet());
    }

    public User deleteFriend(Long id, Long friendId) {
        User user = userStorage.findUserById(id)
                .orElseThrow(notFoundException("Пользователя с идентификатором: {0} - не существует.", id));

        User friendUser = userStorage.findUserById(friendId)
                .orElseThrow(notFoundException("Пользователя с идентификатором: {0} - не существует.", friendId));

        user.getFriendsIds().remove(friendId);
        friendUser.getFriendsIds().remove(id);

        return user;
    }

    public Set<User> getCommonFriends(Long id, Long otherId) {
        User user = userStorage.findUserById(id)
                .orElseThrow(notFoundException("Пользователя с идентификатором: {0} - не существует.", id));

        User otherUser = userStorage.findUserById(otherId)
                .orElseThrow(notFoundException("Пользователя с идентификатором: {0} - не существует.", otherId));

        Set<Long> idsIntersection = findIdsIntersection(user.getFriendsIds(), otherUser.getFriendsIds());

        return userStorage.getUsers().stream()
                .filter(u -> idsIntersection.contains(u.getId()))
                .collect(Collectors.toSet());
    }

    private User fillEmptyNameWithLogin(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return user;
    }

    private Set<Long> findIdsIntersection(Set<Long> firstIds, Set<Long> secondIds) {
        Set<Long> intersection = new HashSet<>(firstIds);
        intersection.retainAll(secondIds);

        return intersection;
    }
}
