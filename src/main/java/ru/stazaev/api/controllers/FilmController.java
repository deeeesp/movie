package ru.stazaev.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.controllers.intSwagger.IFilmController;
import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.FilmDtoWithCover;
import ru.stazaev.api.dto.response.FilmSearchDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.api.services.SelectionService;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "https://movie-genie-131a7.web.app")
@RestController
@RequestMapping("/api/film")
public class FilmController implements IFilmController {
    private final String SAVE_PATH = "/save";
    private final String DELETE = "/delete/{film_id}";
    private final String ALL_FILMS = "/get/films";
    private final String FIND_BY_ID = "/get/{film_id}";
    private final String FIND_BY_ID_WITH_COVER = "/with-cover/{film_id}";
    private final String FIND_FILM = "/get/{title}";
    private final String UPDATE_COVER = "/cover-update";
    private final String GET_COVER = "/cover/{film_id}";
    private final String ADD_FILM_TO_WLL_WATCH_SELECTION = "/{film_id}/will-watch-sel";
    private final String ADD_FILM_TO_CUSTOM_SELECTION = "/{film_id}/cust-sel/{selection_id}";


    private final FilmService filmService;
    private final SelectionService selectionService;


    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FilmDtoWithCover> getFilmById(@PathVariable("film_id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmByIdWithCover(id));
    }

    @GetMapping(ALL_FILMS)
    public ResponseEntity<List<FilmDtoWithCover>> getFilms() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getAllFilms());
    }

    @GetMapping(FIND_FILM)
    public ResponseEntity<FilmSearchDto> getFilm(@PathVariable String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmByTitleOrPlot(title));
    }

    @PostMapping(SAVE_PATH)
    public ResponseEntity<Void> saveFilm(
            @RequestBody FilmDto filmDTO,
            Authentication authentication) {
        filmService.saveFilm(filmDTO, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping(DELETE)
    public ResponseEntity<Void> deleteFilm(
            @PathVariable("film_id") long id,
            Authentication authentication) {
        filmService.deleteFilmById(id, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(UPDATE_COVER)
    public ResponseEntity<Void> updateCover(
            @RequestBody UpdateFilmCoverDto filmCoverDto,
            Authentication authentication) {
        filmService.updateFilmCover(filmCoverDto, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping(GET_COVER)
    public ResponseEntity<ResponsePictureDto> getCover(@PathVariable("film_id") long id) {
        var cover = filmService.getFilmCover(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cover);
    }

    @PostMapping(ADD_FILM_TO_WLL_WATCH_SELECTION)
    public ResponseEntity<Void> addToWillWatchSel(
            @PathVariable("film_id") long filmId,
            Authentication authentication) {
        selectionService.addFilmToWillWatch(authentication.getName(), filmId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(ADD_FILM_TO_CUSTOM_SELECTION)
    public ResponseEntity<Void> addToCustomSel(
            @PathVariable("film_id") long filmId,
            @PathVariable("selection_id") long selectionId,
            Authentication authentication) {
        selectionService.addFilmToCustomSelection(selectionId, filmId, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
