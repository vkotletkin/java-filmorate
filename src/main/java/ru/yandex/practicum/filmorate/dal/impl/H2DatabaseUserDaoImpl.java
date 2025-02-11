package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.UserDao;
import ru.yandex.practicum.filmorate.dal.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository("h2User")
@RequiredArgsConstructor
public class H2DatabaseUserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Collection<User> findAll() {
        String query = "SELECT user_id, email, login, name, birthday FROM users";
        return jdbcTemplate.query(query, new UserRowMapper());
    }

    @Override
    public User createUser(User user) {
        String query = """
                INSERT INTO users (user_id, email, login, name, birthday)
                VALUES (:user_id, :email, :login, :name, :birthday);
                """;

        return addUserData(user, query);
    }

    @Override
    public User updateUser(User user) {
        String query = """
                UPDATE users
                SET user_id  = :user_id,
                    email    = :email,
                    login    = :login,
                    name     = :name,
                    birthday = :birthday
                WHERE user_id = :user_id;
                """;
        return addUserData(user, query);
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
        String query = """
                SELECT user_id, email, login, name, birthday
                FROM users
                WHERE user_id = :user_id;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", id);

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, params, new UserRowMapper()));
    }

    private User addUserData(User user, String query) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", user.getId());
        params.addValue("email", user.getEmail());
        params.addValue("login", user.getLogin());
        params.addValue("name", user.getName());
        params.addValue("birthday", user.getBirthday());
        jdbcTemplate.update(query, params);

        return user;
    }
}
