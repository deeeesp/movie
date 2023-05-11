package ru.stazaev.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.services.FilmService;

import java.util.List;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final String SAVE_PATH = "/save";
    private final String DELETE_BY_ID = "/delete/{id}";
    private final String FIND_BY_ID = "/{id}";
    private final String FIND_BY_TITLE = "/title-search/{title}";
    private final String FIND_BY_TITLE_RATIO = "/title-ratio-search/{title}";
    private final String FIND_BY_PLOT_RATIO = "/plot-ratio-search/{title}";


    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(FIND_BY_ID)
    public FilmDto getFilm(@PathVariable Long id) {

        return filmService.getFilmById(id);
    }

    @GetMapping(FIND_BY_TITLE)
    public List<FilmDto> getFilmByTitle(@PathVariable String title) {return filmService.getByTitle(title);}

    @GetMapping(FIND_BY_TITLE_RATIO)
    public List<FilmDto> getFilmByTitleRatio(@PathVariable String title) {return filmService.getByTitleRatio(title);}

    @GetMapping(FIND_BY_PLOT_RATIO)
    public List<FilmDto> getFilmByPlotRatio(@PathVariable String title) {return filmService.getByPlotRatio(title);}


    @PostMapping(SAVE_PATH)
    public String saveFilm(@RequestBody FilmDto filmDTO) {
        System.out.println();
        filmService.saveFilm(filmDTO);
        return "success";
    }

    @PostMapping(DELETE_BY_ID)
    public String addFilmToSelection(@PathVariable long id) {
        filmService.deleteFilmById(id);
        return "success";
    }


}
