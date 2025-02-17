package ru.yandex.practicum.filmorate.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.MpaDao;
import ru.yandex.practicum.filmorate.dal.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.entity.Mpa;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.dal.impl.query.MpaQuery.QUERY_FIND_ALL;
import static ru.yandex.practicum.filmorate.dal.impl.query.MpaQuery.QUERY_FIND_BY_ID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2DatabaseMpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Mpa> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, new MpaRowMapper(), id));
    }

    @Override
    public List<Mpa> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new MpaRowMapper());
    }
}
