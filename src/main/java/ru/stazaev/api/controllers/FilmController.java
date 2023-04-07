package ru.stazaev.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.api.services.FilmService;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final String SAVE_PATH = "/save";
    private final String DELETE_BY_ID = "/delete/{id}";

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public FilmDTO getFilm(@PathVariable Long id){
        return filmService.getFilmById(id);
    }

    @PostMapping(SAVE_PATH)
    public String saveFilm(@RequestBody FilmDTO filmDTO){
        System.out.println();
        filmService.saveFilm(filmDTO);
        return "success";
    }

    @PostMapping(DELETE_BY_ID)
    public String addFilmToSelection(@PathVariable long id){
        filmService.deleteFilmById(id);
        return "success";
    }
}
