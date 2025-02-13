package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.RelationDao;
import ru.yandex.practicum.filmorate.dal.mapper.RelationRowMapper;
import ru.yandex.practicum.filmorate.entity.Relation;

import java.util.List;

import static ru.yandex.practicum.filmorate.dal.impl.query.RelationQuery.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseRelationDaoImpl implements RelationDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createRelation(Long userId, Long friendId, Long relationStatus) {
        jdbcTemplate.update(QUERY_CREATE_RELATION, userId, friendId, relationStatus);
    }

    @Override
    public void deleteRelation(Long userId, Long friendId) {
        jdbcTemplate.update(QUERY_DELETE_RELATION, userId, friendId);
        jdbcTemplate.update(QUERY_DELETE_RELATION_CONFIRMED, userId);
    }

    @Override
    public List<Relation> findFriendsByUserId(Long userId) {
        return jdbcTemplate.query(QUERY_FIND_FRIENDS_FOR_USER, new RelationRowMapper(), userId, userId);
    }

    @Override
    public List<Relation> findCommonFriends(Long firstUserId, Long secondUserId) {
        return jdbcTemplate.query(QUERY_FIND_RELATIONS_FOR_USERS, new RelationRowMapper(),
                firstUserId, firstUserId, secondUserId, secondUserId);
    }

    @Override
    public List<Relation> findRelationByUserIds(Long userId, Long friendId) {
        return jdbcTemplate.query(QUERY_FIND_RELATION_ON_USERS_IDS, new RelationRowMapper(),
                userId, friendId, userId, friendId);
    }
}
