package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.entity.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmGenreRowMapper implements RowMapper<FilmGenre> {

    @Override
    public FilmGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
        FilmGenre filmGenre = new FilmGenre();
        filmGenre.setId(rs.getLong("id"));
        filmGenre.setFilmId(rs.getLong("film_id"));
        filmGenre.setGenreId(rs.getLong("genre_id"));
        return filmGenre;
    }
}
