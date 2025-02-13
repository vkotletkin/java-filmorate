package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

@Slf4j
@Repository("h2User")
@RequiredArgsConstructor
public class H2DatabaseUserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public static String queryCreateUser = """
            INSERT INTO users (email, login, name, birthday)
            VALUES (?, ?, ?, ?);
            """;

    public static String queryUpdateUser = """
            UPDATE users
            SET email    = ?,
                login    = ?,
                name     = ?,
                birthday = ?
            WHERE user_id = ?;
            """;


    @Override
    public Collection<User> findAll() {
        String query = "SELECT user_id, email, login, name, birthday FROM users";
        return jdbcTemplate.query(query, new UserRowMapper());
    }

    @Override
    public User createUser(User user) {
        long key = insertMessage(queryCreateUser, user);
        user.setId(key);
        return user;
    }

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

    private long updateMessage(String query, User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setString(4, String.valueOf(user.getBirthday()));
            ps.setString(5, String.valueOf(user.getId()));

            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public User updateUser(User user) {
        long key = updateMessage(queryUpdateUser, user);
        user.setId(key);
        return user;
    }

    @Override
    public Map<String, String> deleteUserById(Long id) {
        String query = """
                DELETE FROM users
                WHERE user_id = :user_id;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", id);
        jdbcTemplate.update(query, params);

        return Map.of("description", String.format("Пользоавтель с идентификатором: %d успешно удален.", id));
    }

    @Override
    public Optional<User> findUserById(Long id) {
        String query = "SELECT user_id, email, login, name, birthday FROM users WHERE user_id = ?";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new UserRowMapper(), id));
    }

//    @Override
//    public List<User> findUserById(Long id) {
//        String query = """
//                SELECT user_id, email, login, name, birthday
//                FROM users
//                WHERE user_id = :user_id;
//                """;
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("user_id", id);
//
//        return jdbcTemplate.query(query, (PreparedStatementSetter) params, new UserRowMapper());
//    }
}
