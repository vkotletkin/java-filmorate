package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.entity.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikeRowMapper implements RowMapper<Like> {
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();
        like.setId(rs.getLong("like_id"));
        like.setFilmId(rs.getLong("film_id"));
        like.setUserId(rs.getLong("user_id"));
        return like;
    }
}
