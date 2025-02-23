package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.entity.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaDao {
    Optional<Mpa> findById(Long id);

    List<Mpa> findAll();
}
