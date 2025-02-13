package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.MpaDao;
import ru.yandex.practicum.filmorate.dal.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.entity.Mpa;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseMpaDaoImpl implements MpaDao {

    private static final String QUERY_FIND_BY_ID = """
            SELECT MPA_ID, RATING FROM MPA
            WHERE MPA_ID = ?
            """;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Mpa> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, new MpaRowMapper(), id));
    }
}
