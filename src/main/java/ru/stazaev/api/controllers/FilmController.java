package ru.stazaev.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    private final String ALL_FILMS = "/films";
    private final String ALL_FILMS_BY_RATING = "/films/rating";
    private final String FIND_BY_ID = "/search-id";
    private final String FIND_FILM = "/search";
    private final String UPDATE_COVER = "/cover-update";
    private final String ADD_FILM_TO_WLL_WATCH_SELECTION = "/{film_id}/will-watch-sel";
    private final String ADD_FILM_TO_CUSTOM_SELECTION = "/{film_id}/cust-sel/{selection_id}";


    private final FilmService filmService;
    private final SelectionService selectionService;


//    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
//    @GetMapping(FIND_BY_ID)
//    public ResponseEntity<FilmDtoWithCover> getFilmById(@PathVariable("film_id") Long id) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(filmService.getFilmById(id));
//    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @Override
    public ResponseEntity<FilmDtoWithCover> getFilmByIdWithCover(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmById(id));
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @GetMapping(ALL_FILMS)
    public ResponseEntity<List<FilmDtoWithCover>> getFilms() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getAllFilms());
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @GetMapping(ALL_FILMS_BY_RATING)
    public ResponseEntity<List<FilmDtoWithCover>> getFilmsSortedByRating() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmsSortedByRating());
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(FIND_FILM)
    public ResponseEntity<FilmSearchDto> getFilm(@RequestBody String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmByTitleOrPlot(title));
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(UPDATE_COVER)
    public ResponseEntity<Void> updateCover(
            @RequestBody UpdateFilmCoverDto filmCoverDto,
            Authentication authentication) {
        filmService.updateFilmCover(filmCoverDto, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(ADD_FILM_TO_WLL_WATCH_SELECTION)
    public ResponseEntity<Void> addToWillWatchSel(
            @PathVariable("film_id") long filmId,
            Authentication authentication) {
        selectionService.addFilmToWillWatch(authentication.getName(), filmId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
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
