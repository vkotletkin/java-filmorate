package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Запрошен возврат списка всех фильмов.");
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Выполняется добавление нового фильма.");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        log.info("Выполняется обновление фильма.");
        return filmService.updateFilm(film);
    }

    @DeleteMapping
    public Map<String, String> delete(@RequestParam Long id) {
        log.info("Выполняется удаление фильма по идентификатору");
        return filmService.deleteFilmById(id);
    }

    @PutMapping("/{id}/like/{user-id}")
    public Film createLike(@PathVariable(name = "id") Long id,
                           @PathVariable(name = "user-id") Long userId) {
        log.info("Выполняется добавление лайка пользователем {} к фильму {}.", userId, id);
        return filmService.createLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{user-id}")
    public Map<String, String> deleteLike(@PathVariable(name = "id") Long id,
                                          @PathVariable(name = "user-id") Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopularFilms(@RequestParam(defaultValue = "10") Long count) {
        return filmService.findPopularFilms(count);
    }

}
