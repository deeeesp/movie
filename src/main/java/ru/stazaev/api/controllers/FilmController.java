package ru.stazaev.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.FilmSearchDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.api.services.SelectionService;

@RequiredArgsConstructor
@Tag(name = "Film API", description = "Allows to find films")
@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final String SAVE_PATH = "/save";
    private final String DELETE = "/delete/{film_id}";
    private final String FIND_BY_ID = "/{film_id}";
    private final String FIND_FILM = "/search/{title}";
    private final String UPDATE_COVER = "/cover-update";
    private final String GET_COVER = "/{film_id}/cover";
    private final String ADD_FILM_TO_FAVORITE_SELECTION = "/{film_id}/fav-sel";
    private final String ADD_FILM_TO_CUSTOM_SELECTION = "/{film_id}/cust-sel/{selection_id}";


    private final FilmService filmService;
    private final SelectionService selectionService;


    @Operation(summary = "Get film by id")
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FilmDto> getFilmById(@PathVariable("film_id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmById(id));
    }


    @Operation(summary = "Find film by title and plot")
    @GetMapping(FIND_FILM)
    public ResponseEntity<FilmSearchDto> getFilm(@PathVariable String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmByTitleOrPlot(title));
    }

/*
    @Operation(summary = "Find film by title")
    @GetMapping(FIND_BY_TITLE)
    public ResponseEntity<List<FilmDto>> getFilmByTitle(@PathVariable String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getByTitle(title));
    }

    @Operation(summary = "Find film by content")
    @GetMapping(FIND_BY_TITLE_RATIO)
    public ResponseEntity<List<FilmDto>> getFilmByTitleRatio(@PathVariable String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getByTitleRatio(title));
    }

    @Operation(summary = "Find film by plot ratio in title")
    @GetMapping(FIND_BY_PLOT_RATIO)
    public ResponseEntity<List<FilmDto>> getFilmByPlotRatio(@PathVariable String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getByPlotRatio(title));
    }

 */


    @Operation(summary = "Save new film")
    @PostMapping(SAVE_PATH)
    public ResponseEntity<Void> saveFilm(
            @RequestBody FilmDto filmDTO,
            Authentication authentication) {
        filmService.saveFilm(filmDTO, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Delete film by id")
    @PostMapping(DELETE)
    public ResponseEntity<Void> deleteFilm(
            @PathVariable("film_id") long id,
            Authentication authentication) {
        filmService.deleteFilmById(id, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Update film cover by id")
    @PostMapping(UPDATE_COVER)
    public ResponseEntity<Void> updateCover(
            @ModelAttribute UpdateFilmCoverDto filmCoverDto,
            Authentication authentication) {
        filmService.updateFilmCover(filmCoverDto, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Get film cover by id")
    @GetMapping(GET_COVER)
    public ResponseEntity<ResponsePictureDto> getCover(@PathVariable("film_id") long id) {
        var cover = filmService.getFilmCover(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cover);
    }

    @Operation(summary = "Add film at favorite selection")
    @PostMapping(ADD_FILM_TO_FAVORITE_SELECTION)
    public ResponseEntity<Void> addToFavoriteSel(
            @PathVariable("film_id") long filmId,
            Authentication authentication) {
        selectionService.addFilmToFavorite(authentication.getName(), filmId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Add film at custom selection")
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
