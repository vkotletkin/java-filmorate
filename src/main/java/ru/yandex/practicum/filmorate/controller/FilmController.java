package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.entity.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    private final String likeURI = "/{id}/like/{user-id}";

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Запрошен возврат списка всех фильмов.");
        return filmService.findAll();
    }

    @PostMapping
    public FilmDto create(@Valid @RequestBody FilmDto filmDto) {
        log.info("Выполняется добавление нового фильма.");
        return filmService.createFilm(filmDto);
    }

    @PutMapping
    public Film update(@RequestBody FilmDto filmDto) {
        log.info("Выполняется обновление фильма.");
        return filmService.updateFilm(filmDto);
    }

    @DeleteMapping
    public Map<String, String> delete(@RequestParam Long id) {
        log.info("Выполняется удаление фильма по идентификатору");
        return filmService.deleteFilmById(id);
    }

    @PutMapping(likeURI)
    public Film createLike(@PathVariable(name = "id") Long id,
                           @PathVariable(name = "user-id") Long userId) {
        log.info("Выполняется добавление лайка пользователем {} к фильму {}.", userId, id);
        return filmService.createLike(id, userId);
    }

    @DeleteMapping(likeURI)
    public Map<String, String> deleteLike(@PathVariable(name = "id") Long id,
                                          @PathVariable(name = "user-id") Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopularFilms(@RequestParam(defaultValue = "10") Long count) {
        return filmService.findPopularFilms(count);
    }

    @GetMapping("/{id}")
    public FilmDto getFilm(@PathVariable Long id) {
        return filmService.getFilmFull(id);
    }

}
