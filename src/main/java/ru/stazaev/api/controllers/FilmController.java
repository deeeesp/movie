package ru.stazaev.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.request.DeleteFilmDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.services.FilmService;

import java.util.List;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final String SAVE_PATH = "/save";
    private final String DELETE = "/delete";
    private final String FIND_BY_ID = "/{id}";
    private final String FIND_BY_TITLE = "/title-search/{title}";
    private final String FIND_BY_TITLE_RATIO = "/title-ratio-search/{title}";
    private final String FIND_BY_PLOT_RATIO = "/plot-ratio-search/{title}";


    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FilmDto> getFilm(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmById(id));
    }

    @GetMapping(FIND_BY_TITLE)
    public ResponseEntity<List<FilmDto>> getFilmByTitle(@PathVariable String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getByTitle(title));
    }

    @GetMapping(FIND_BY_TITLE_RATIO)
    public ResponseEntity<List<FilmDto>> getFilmByTitleRatio(@PathVariable String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getByTitleRatio(title));
    }

    @GetMapping(FIND_BY_PLOT_RATIO)
    public ResponseEntity<List<FilmDto>> getFilmByPlotRatio(@PathVariable String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getByPlotRatio(title));
    }


    @PostMapping(SAVE_PATH)
    public ResponseEntity<Void> saveFilm(@RequestBody FilmDto filmDTO) {
        filmService.saveFilm(filmDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping(DELETE)
    public ResponseEntity<Void> deleteFilm(@RequestBody DeleteFilmDto filmDto) {
        filmService.deleteFilmById(filmDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


}
