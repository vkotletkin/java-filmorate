package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.RelationDao;
import ru.yandex.practicum.filmorate.dal.UserDao;
import ru.yandex.practicum.filmorate.entity.Relation;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.*;

import static ru.yandex.practicum.filmorate.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

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
        userDao.findUserById(user.getId()).stream().findFirst()
                .orElseThrow(notFoundException("Пользователь с логином: {0} - не существует.", user.getLogin()));
        return userDao.updateUser(user);
    }

    public Map<String, String> deleteUserById(Long id) {
        return userDao.deleteUserById(id);
    }

    public User createFriend(Long id, Long friendId) {
        User user = userDao.findUserById(id).stream().findFirst()
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        User friendUser = userDao.findUserById(friendId).stream().findFirst()
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", friendId));

        // TODO: dao create users

        Relation currentRelation = relationDao.findRelationByUserIds(user.getId(), friendId);
        if (currentRelation != null) {
            if (currentRelation.getRelationStatus() == 2L) {
                relationDao.createRelation(user.getId(), friendId, 3L);
            }
            // if 3: exists - pohui, if 1L - exists pohui
        } else {
            relationDao.createRelation(user.getId(), friendUser.getId(), 1L);
        }

        return user;
    }

    public Set<User> getUserFriends(Long id) {
        userDao.findUserById(id).stream().findFirst()
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        List<Relation> a = relationDao.findFriendsByUserId(id);

        return new HashSet<>(userDao.findAll());
        // todo: refactor
    }

    public Map<String, String> deleteFriend(Long id, Long friendId) {
        User user = userDao.findUserById(id).stream().findFirst()
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        User friendUser = userDao.findUserById(friendId).stream().findFirst()
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", friendId));


        return Map.of("description",
                String.format("Пользователи с идентификаторами: %d и %d больше не друзья!", id, friendId));
    }

    public Set<User> getCommonFriends(Long id, Long otherId) {
        User user = userDao.findUserById(id).stream().findFirst()
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        User otherUser = userDao.findUserById(otherId).stream().findFirst()
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", otherId));

        //Set<Long> idsIntersection = findIdsIntersection(user.getFriendsIds(), otherUser.getFriendsIds());

//        return userDao.findAll().stream()
//                .filter(u -> idsIntersection.contains(u.getId()))
//                .collect(Collectors.toSet());
        // TODO; refactor
        return new HashSet<>(userDao.findAll());
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
