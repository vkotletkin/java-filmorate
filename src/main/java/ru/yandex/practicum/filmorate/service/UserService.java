package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.RelationDao;
import ru.yandex.practicum.filmorate.dal.UserDao;
import ru.yandex.practicum.filmorate.entity.Relation;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_ERROR_MESSAGE = "Пользователь с идентификатором: {0} - не существует.";
    private final UserDao userDao;
    private final RelationDao relationDao;

    public Collection<User> findAll() {
        return userDao.findAll();
    }

    public User createUser(User user) {
        User userProcessed = fillEmptyNameWithLogin(user);
        return userDao.createUser(userProcessed);
    }

    public User updateUser(User user) {
        userDao.findUserById(user.getId())
                .orElseThrow(notFoundException(USER_ERROR_MESSAGE, user.getLogin()));
        userDao.updateUser(user);
        return user;
    }

    public Map<String, String> deleteUserById(Long id) {
        return userDao.deleteUserById(id);
    }

    public User createFriend(Long id, Long friendId) {
        User user = userDao.findUserById(id)
                .orElseThrow(notFoundException(USER_ERROR_MESSAGE, id));

        User friendUser = userDao.findUserById(friendId)
                .orElseThrow(notFoundException(USER_ERROR_MESSAGE, friendId));

        List<Relation> currentRelation = relationDao.findRelationByUserIds(user.getId(), friendId);

        if (!currentRelation.isEmpty()) {
            if (currentRelation.getFirst().getRelationStatus() == 2L) {
                relationDao.createRelation(user.getId(), friendId, 3L);
            }
        } else {
            relationDao.createRelation(user.getId(), friendUser.getId(), 1L);
        }

        return user;
    }

    public Set<User> getUserFriends(Long id) {
        userDao.findUserById(id)
                .orElseThrow(notFoundException(USER_ERROR_MESSAGE, id));

        List<Long> friendsIds = getFriendsIds(id);

        return friendsIds.stream().map(u -> userDao.findUserById(u)
                        .orElseThrow(notFoundException(USER_ERROR_MESSAGE, id)))
                .collect(Collectors.toSet());
    }

    private List<Long> getFriendsIds(Long id) {
        List<Long> idsOnUserColumn = relationDao.findFriendsByUserId(id)
                .stream()
                .map(Relation::getUserId)
                .filter(u -> !u.equals(id)).toList();

        List<Long> idsOnFriendColumn = relationDao.findFriendsByUserId(id)
                .stream()
                .map(Relation::getFriendId)
                .filter(u -> !u.equals(id)).toList();

        List<Long> allIds = new ArrayList<>();
        allIds.addAll(idsOnUserColumn);
        allIds.addAll(idsOnFriendColumn);

        return allIds;
    }

    public Map<String, String> deleteFriend(Long id, Long friendId) {
        userDao.findUserById(id)
                .orElseThrow(notFoundException(USER_ERROR_MESSAGE, id));
        userDao.findUserById(friendId)
                .orElseThrow(notFoundException(USER_ERROR_MESSAGE, friendId));

        relationDao.deleteRelation(id, friendId);

        return Map.of("description",
                String.format("Пользователи с идентификаторами: %d и %d больше не друзья!", id, friendId));
    }

    //
    public Set<User> getCommonFriends(Long id, Long otherId) {
        userDao.findUserById(id)
                .orElseThrow(notFoundException(USER_ERROR_MESSAGE, id));

        userDao.findUserById(otherId)
                .orElseThrow(notFoundException(USER_ERROR_MESSAGE, otherId));

        List<Long> firstUserIds = getFriendsIds(id);
        List<Long> secondUserIds = getFriendsIds(otherId);

        return findIdsIntersection(new HashSet<>(firstUserIds), new HashSet<>(secondUserIds)).stream()
                .map(u -> userDao.findUserById(u)
                        .orElseThrow(notFoundException(USER_ERROR_MESSAGE, u)))
                .collect(Collectors.toSet());
    }

    //
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
