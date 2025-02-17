package ru.yandex.practicum.filmorate.dal.impl.query;

public class GenreQuery {
    public static final String QUERY_FOR_GET_FILM_GENRES = """
            SELECT DISTINCT f.GENRE_ID AS GENRE_ID, g.GENRE_NAME AS GENRE_NAME
                             FROM FILMS_GENRES f
                             INNER JOIN GENRES g ON f.GENRE_ID = g.GENRE_ID
                             WHERE f.film_id = ?""";

    public static final String QUERY_BATCH_INSERT = "INSERT INTO FILMS_GENRES (FILM_ID, GENRE_ID) VALUES (?, ?)";

    public static final String QUERY_FIND_ALL = "SELECT GENRE_ID, GENRE_NAME FROM GENRES;";

    public static final String QUERY_FIND_BY_ID = "SELECT GENRE_ID, GENRE_NAME FROM GENRES WHERE GENRE_ID = ?;";
}
