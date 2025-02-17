package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final FilmService filmService;

    @GetMapping
    public List<GenreDto> getGenres() {
        return filmService.findGenres();
    }

    @GetMapping("/{id}")
    public GenreDto getGenre(@PathVariable Long id) {
        return filmService.findGenreById(id);
    }
}
