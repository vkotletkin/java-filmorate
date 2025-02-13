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
                .orElseThrow(notFoundException("Пользователь с логином: {0} - не существует.", user.getLogin()));
        userDao.updateUser(user);
        return user;
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

        //Relation currentRelation = relationDao.findRelationByUserIds(user.getId(), friendId).stream().findFirst().orElseThrow(notFoundException("Not found"));
        List<Relation> currentRelation = relationDao.findRelationByUserIds(user.getId(), friendId);

        if (!currentRelation.isEmpty()) {
            if (currentRelation.getFirst().getRelationStatus() == 2L) {
                relationDao.createRelation(user.getId(), friendId, 3L);
            }
            // if 3: exists - pohui, if 1L - exists pohui
        } else {
            relationDao.createRelation(user.getId(), friendUser.getId(), 1L);
        }

        return user;
    }

    public Set<User> getUserFriends(Long id) {
        userDao.findUserById(id)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        List<Long> inputs = relationDao.findFriendsByUserId(id)
                .stream()
                .map(Relation::getUserId)
                .filter(u -> !u.equals(id)).toList();

        List<Long> inputs2 = relationDao.findFriendsByUserId(id)
                .stream()
                .map(Relation::getFriendId)
                .filter(u -> !u.equals(id)).toList();

        List<Long> a = new ArrayList<>(inputs);
        a.addAll(inputs2);

        return a.stream().map(u -> userDao.findUserById(u).get()).collect(Collectors.toSet());
        // todo: refactor
    }

    public Map<String, String> deleteFriend(Long id, Long friendId) {
        User user = userDao.findUserById(id)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        User friendUser = userDao.findUserById(friendId)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", friendId));

        relationDao.deleteRelation(user.getId(), friendId);

        return Map.of("description",
                String.format("Пользователи с идентификаторами: %d и %d больше не друзья!", id, friendId));
    }

    //
    public Set<User> getCommonFriends(Long id, Long otherId) {
        User user = userDao.findUserById(id)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", id));

        User otherUser = userDao.findUserById(otherId)
                .orElseThrow(notFoundException("Пользователь с идентификатором: {0} - не существует.", otherId));

        //Set<Long> idsIntersection = findIdsIntersection(user.getFriendsIds(), otherUser.getFriendsIds());

        List<Long> inputs = relationDao.findFriendsByUserId(id)
                .stream()
                .map(Relation::getUserId)
                .filter(u -> !u.equals(id)).toList();

        List<Long> inputs2 = relationDao.findFriendsByUserId(id)
                .stream()
                .map(Relation::getFriendId)
                .filter(u -> !u.equals(id)).toList();

        List<Long> inputs3 = relationDao.findFriendsByUserId(otherId)
                .stream()
                .map(Relation::getUserId)
                .filter(u -> !u.equals(otherId)).toList();

        List<Long> inputs4 = relationDao.findFriendsByUserId(otherId)
                .stream()
                .map(Relation::getFriendId)
                .filter(u -> !u.equals(otherId)).toList();

        List<Long> a = new ArrayList<>(inputs);
        a.addAll(inputs2);

        List<Long> b = new ArrayList<>(inputs3);
        b.addAll(inputs4);

        return findIdsIntersection(new HashSet<>(a), new HashSet<>(b)).stream()
                .map(u -> userDao.findUserById(u).get()).collect(Collectors.toSet());
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
