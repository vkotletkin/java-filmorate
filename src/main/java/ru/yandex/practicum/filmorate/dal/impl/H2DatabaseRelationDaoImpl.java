package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.RelationDao;
import ru.yandex.practicum.filmorate.dal.mapper.RelationRowMapper;
import ru.yandex.practicum.filmorate.entity.Relation;
import ru.yandex.practicum.filmorate.entity.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseRelationDaoImpl implements RelationDao {

    private static final String queryCreateRelation = """
            INSERT INTO FRIENDS_RELATIONS (user_id, friend_id, relation_status)
            VALUES (?, ?, ?)
            """;
    private static final String queryDeleteRelationConfirmed = """
            UPDATE FRIENDS_RELATIONS
            SET RELATION_STATUS = 2
            WHERE user_id = ?;
            """;
    private static final String queryDeleteRelation = """
            DELETE FROM FRIENDS_RELATIONS
            WHERE user_id = ? AND RELATION_STATUS = 1
            OR FRIEND_ID = ? AND RELATION_STATUS = 2;
            """;
    private final JdbcTemplate jdbcTemplate;

    private long insertMessage(String query, User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setString(4, String.valueOf(user.getBirthday()));

            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void createRelation(Long userId, Long friendId, Long relationStatus) {
        jdbcTemplate.update(queryCreateRelation, userId, friendId, relationStatus);
    }

    @Override
    public void deleteRelation(Long userId, Long friendId) {
        jdbcTemplate.update(queryDeleteRelation, userId, friendId);
        jdbcTemplate.update(queryDeleteRelationConfirmed, userId);
    }

    @Override
    public List<Relation> findFriendsByUserId(Long userId) {
        String query = """
                SELECT id, user_id, FRIEND_ID, RELATION_STATUS
                FROM FRIENDS_RELATIONS
                WHERE (user_id = ? AND (RELATION_STATUS = 1 OR RELATION_STATUS = 3))
                UNION
                SELECT id, user_id, FRIEND_ID, RELATION_STATUS
                FROM FRIENDS_RELATIONS
                WHERE (FRIEND_ID = ? AND (RELATION_STATUS = 2 OR RELATION_STATUS = 3))
                """;
        return jdbcTemplate.query(query, new RelationRowMapper(), userId, userId);
    }

    @Override
    public List<Relation> findCommonFriends(Long firstUserId, Long secondUserId) {
        String query = """
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
        return jdbcTemplate.query(query, new RelationRowMapper(), firstUserId, firstUserId, secondUserId, secondUserId);
    }

    @Override
    public List<Relation> findRelationByUserIds(Long userId, Long friendId) {
        String query = "SELECT id, user_id, friend_id, relation_status FROM FRIENDS_RELATIONS WHERE (user_id = ? OR user_id = ?) AND (friend_id = ? OR friend_id = ?)";
        return jdbcTemplate.query(query, new RelationRowMapper(), userId, friendId, userId, friendId);
    }
}
