package ru.yandex.practicum.filmorate.dal.impl.query;

public class LikeQuery {
    public static final String QUERY_FIND_ALL = "SELECT like_id, film_id, user_id FROM film_likes";
    public static final String QUERY_ADD_LIKE = "INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)";
    public static final String QUERY_DELETE_LIKE = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";
}
