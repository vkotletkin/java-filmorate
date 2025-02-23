package ru.yandex.practicum.filmorate.dal.impl.query;

public class UserQuery {
    public static final String QUERY_UPDATE_USER = """
            UPDATE users
            SET email    = ?,
                login    = ?,
                name     = ?,
                birthday = ?
            WHERE user_id = ?;
            """;

    public static final String QUERY_DELETE_USER_BY_ID = """
            DELETE FROM users
            WHERE user_id = ?;
            """;

    public static final String QUERY_FIND_USER_BY_ID = """
            SELECT user_id, email, login, name, birthday
            FROM users
            WHERE user_id = ?;
            """;

    public static final String QUERY_CREATE_USER = """
            INSERT INTO users (email, login, name, birthday)
            VALUES (?, ?, ?, ?);
            """;

    public static final String QUERY_FIND_ALL = """
            SELECT user_id, email, login, name, birthday
            FROM users
            """;
}
