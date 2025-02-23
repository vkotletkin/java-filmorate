package ru.yandex.practicum.filmorate.dal.impl.query;

public class MpaQuery {
    public static final String QUERY_FIND_BY_ID = """
            SELECT MPA_ID, NAME FROM MPA
            WHERE MPA_ID = ?
            """;

    public static final String QUERY_FIND_ALL = "SELECT MPA_ID, NAME FROM MPA";
}
