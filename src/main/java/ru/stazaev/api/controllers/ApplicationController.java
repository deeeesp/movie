package ru.stazaev.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.api.services.FilmService;

import java.util.List;

@RestController
public class ApplicationController {
    private final FilmService filmService;
    private static final String MAIN_PAGE = "/api/";

    public ApplicationController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(MAIN_PAGE)
    public List<FilmDTO> getMainPage(){
        return filmService.getTopFilms();
    }
}
