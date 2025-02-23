package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.entity.Relation;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RelationRowMapper implements RowMapper<Relation> {
    @Override
    public Relation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Relation relation = new Relation();
        relation.setId(rs.getLong("id"));
        relation.setUserId(rs.getLong("user_id"));
        relation.setFriendId(rs.getLong("friend_id"));
        relation.setRelationStatus(rs.getLong("relation_status"));
        return relation;
    }
}
