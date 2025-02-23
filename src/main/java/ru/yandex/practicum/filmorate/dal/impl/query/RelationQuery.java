package ru.yandex.practicum.filmorate.dal.impl.query;

public class RelationQuery {
    public static final String QUERY_CREATE_RELATION = """
            INSERT INTO FRIENDS_RELATIONS (user_id, friend_id, relation_status)
            VALUES (?, ?, ?)
            """;

    public static final String QUERY_DELETE_RELATION_CONFIRMED = """
            UPDATE FRIENDS_RELATIONS
            SET RELATION_STATUS = 2
            WHERE user_id = ?;
            """;

    public static final String QUERY_DELETE_RELATION = """
            DELETE FROM FRIENDS_RELATIONS
            WHERE user_id = ? AND RELATION_STATUS = 1
            OR FRIEND_ID = ? AND RELATION_STATUS = 2;
            """;

    public static final String QUERY_FIND_RELATIONS_FOR_USERS = """
            SELECT id, user_id, FRIEND_ID, RELATION_STATUS
            FROM FRIENDS_RELATIONS
            WHERE (user_id = ? AND (RELATION_STATUS = 1 OR RELATION_STATUS = 3))
            UNION
            SELECT id, user_id, FRIEND_ID, RELATION_STATUS
            FROM FRIENDS_RELATIONS
            WHERE (FRIEND_ID = ? AND (RELATION_STATUS = 2 OR RELATION_STATUS = 3))
            INTERSECT
            SELECT id, user_id, FRIEND_ID, RELATION_STATUS
            FROM FRIENDS_RELATIONS
            WHERE (user_id = ? AND (RELATION_STATUS = 1 OR RELATION_STATUS = 3))
            UNION
            SELECT id, user_id, FRIEND_ID, RELATION_STATUS
            FROM FRIENDS_RELATIONS
            WHERE (FRIEND_ID = ? AND (RELATION_STATUS = 2 OR RELATION_STATUS = 3))
            """;

    public static final String QUERY_FIND_FRIENDS_FOR_USER = """
            SELECT id, user_id, FRIEND_ID, RELATION_STATUS
            FROM FRIENDS_RELATIONS
            WHERE (user_id = ? AND (RELATION_STATUS = 1 OR RELATION_STATUS = 3))
            UNION
            SELECT id, user_id, FRIEND_ID, RELATION_STATUS
            FROM FRIENDS_RELATIONS
            WHERE (FRIEND_ID = ? AND (RELATION_STATUS = 2 OR RELATION_STATUS = 3))
            """;

    public static final String QUERY_FIND_RELATION_ON_USERS_IDS = """
            SELECT id, user_id, friend_id, relation_status
            FROM FRIENDS_RELATIONS
            WHERE (user_id = ? OR user_id = ?)
            AND (friend_id = ? OR friend_id = ?)
            """;
}
