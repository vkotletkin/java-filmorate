package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.RelationDao;
import ru.yandex.practicum.filmorate.dal.mapper.RelationRowMapper;
import ru.yandex.practicum.filmorate.entity.Relation;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseRelationDaoImpl implements RelationDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public void createRelation(Long userId, Long friendId, Long relationStatus) {

    }

    @Override
    public void deleteRelation(Long userId, Long friendId) {

    }

    @Override
    public List<Relation> findFriendsByUserId(Long userId) {
        String query = """
                SELECT id, user_id, FRIEND_ID, RELATION_STATUS
                FROM FRIENDS_RELATIONS
                WHERE (user_id = :user_id AND (RELATION_STATUS = 1 OR RELATION_STATUS = 3))
                UNION
                SELECT id, user_id, FRIEND_ID, RELATION_STATUS
                FROM FRIENDS_RELATIONS
                WHERE (FRIEND_ID = :user_id AND (RELATION_STATUS = 2 OR RELATION_STATUS = 3))
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return jdbcTemplate.query(query, params, new RelationRowMapper());
    }

    @Override
    public List<User> findCommonFriends(Long firstUserId, Long secondUserId) {
        return List.of();
    }

    @Override
    public Relation findRelationByUserIds(Long userId, Long friendId) {
        String query = "SELECT id, user_id, friend_id, relation_status FROM FRIENDS_RELATIONS WHERE user_id = :userId AND friend_id = :friendId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("friendId", friendId);
        return jdbcTemplate.queryForObject(query, params, Relation.class);
    }
}
