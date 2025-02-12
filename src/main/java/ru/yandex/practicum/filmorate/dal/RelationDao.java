package ru.yandex.practicum.filmorate.dal;


import ru.yandex.practicum.filmorate.entity.Relation;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.List;

public interface RelationDao {
    void createRelation(Long userId, Long friendId, Long relationStatus);

    void deleteRelation(Long userId, Long friendId);

    List<Relation> findFriendsByUserId(Long userId);

    List<User> findCommonFriends(Long firstUserId, Long secondUserId);

    Relation findRelationByUserIds(Long userId, Long friendId);
}
