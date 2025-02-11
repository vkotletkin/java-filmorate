package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.UserDao;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public Collection<User> findAll() {
        return userDao.findAll();
    }

    public User createUser(User user) {
//        user.setFriendsIds(new HashSet<>());
        User userProcessed = fillEmptyNameWithLogin(user);

        return userDao.createUser(userProcessed);
    }

    public User updateUser(User user) {

        userDao.findUserById(user.getId())
                .orElseThrow(notFoundException("Фильм с идентификатором: {0} - не существует.", user.getId()));

//        if (user.getFriendsIds() == null) {
//            user.setFriendsIds(new HashSet<>());
//        }

        return userDao.updateUser(user);
    }

    public Map<String, String> deleteUserById(Long id) {
        return userDao.deleteUserById(id);
    }

    public User createFriend(Long id, Long friendId) {
        User user = userDao.findUserById(id)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        User friendUser = userDao.findUserById(friendId)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", friendId));

        // TODO: dao create users
//        user.getFriendsIds().add(friendId);
//        friendUser.getFriendsIds().add(id);

        return user;
    }

    public Set<User> getUserFriends(Long id) {
        userDao.findUserById(id)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        return userDao.findAll().stream()
                .filter(u -> u.getFriendsIds().contains(id))
                .collect(Collectors.toSet());
    }

    public Map<String, String> deleteFriend(Long id, Long friendId) {
        User user = userDao.findUserById(id)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        User friendUser = userDao.findUserById(friendId)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", friendId));

        user.getFriendsIds().remove(friendId);
        friendUser.getFriendsIds().remove(id);

        return Map.of("description",
                String.format("Пользователи с идентификаторами: %d и %d больше не друзья!", id, friendId));
    }

    public Set<User> getCommonFriends(Long id, Long otherId) {
        User user = userDao.findUserById(id)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        User otherUser = userDao.findUserById(otherId)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", otherId));

        Set<Long> idsIntersection = findIdsIntersection(user.getFriendsIds(), otherUser.getFriendsIds());

        return userDao.findAll().stream()
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
