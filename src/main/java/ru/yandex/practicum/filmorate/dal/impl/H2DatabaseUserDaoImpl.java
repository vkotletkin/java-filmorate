package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.UserDao;
import ru.yandex.practicum.filmorate.dal.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.entity.User;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.dal.impl.query.UserQuery.*;

@Slf4j
@Repository("h2User")
@RequiredArgsConstructor
public class H2DatabaseUserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<User> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new UserRowMapper());
    }

    @Override
    public User createUser(User user) {
        long key = insertMessage(user);
        user.setId(key);
        return user;
    }

    @Override
    public User updateUser(User user) {
        long key = updateMessage(user);
        user.setId(key);
        return user;
    }

    @Override
    public Map<String, String> deleteUserById(Long id) {
        jdbcTemplate.update(QUERY_DELETE_USER_BY_ID, id);
        return Map.of("description", String.format("Пользователь с идентификатором: %d успешно удален.", id));
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY_FIND_USER_BY_ID, new UserRowMapper(), id));
    }

    private long insertMessage(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(QUERY_CREATE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setString(4, String.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private long updateMessage(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(QUERY_UPDATE_USER,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setString(4, String.valueOf(user.getBirthday()));
            ps.setString(5, String.valueOf(user.getId()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
