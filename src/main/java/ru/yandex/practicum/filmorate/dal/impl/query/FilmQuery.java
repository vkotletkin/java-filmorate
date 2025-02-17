package ru.yandex.practicum.filmorate.dal.impl.query;

public class FilmQuery {
    public static final String QUERY_FIND_ALL = """
            SELECT film_id, name, description, release_date, duration, mpa
            FROM films;
            """;
    public static final String QUERY_CREATE_FILM = """
            INSERT INTO films (name, description, release_date, duration, mpa)
            VALUES (?, ?, ?, ?, ?);
            """;

    public static final String QUERY_UPDATE_FILM = """
            UPDATE films
            SET name               = ?,
                description        = ?,
                release_date       = ?,
                duration           = ?,
                mpa = ?
            WHERE film_id = ?;
            """;

    public static final String QUERY_DELETE_FILM_BY_ID = """
            DELETE FROM films
            WHERE film_id = :film_id;
            """;

    public static final String QUERY_FIND_FILM_BY_ID = """
            SELECT film_id, name, description, release_date, duration, mpa
            FROM films
            WHERE film_id = ?
            """;

    public static final String QUERY_FIND_POPULAR_FILMS = """
            SELECT f.FILM_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA
            FROM FILMS f
            INNER JOIN
            (SELECT FILM_ID, COUNT(*) AS LIKE_COUNT
            FROM FILM_LIKES
            GROUP BY FILM_ID
            ORDER BY LIKE_COUNT DESC
            LIMIT ?) lc ON (f.FILM_ID = lc.FILM_ID)
            """;
}
