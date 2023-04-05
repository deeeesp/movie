package ru.stazaev.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.api.services.FilmService;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public FilmDTO getFilm(@PathVariable Long id){
        return filmService.getFilmById(id);
    }
}
