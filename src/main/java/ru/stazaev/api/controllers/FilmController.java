package ru.stazaev.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.request.AddFilmInSelectionDTO;
import ru.stazaev.api.dto.request.DeleteFilmDto;
import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.api.services.SelectionService;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Film API", description = "Allows to find films")
@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final String SAVE_PATH = "/save";
    private final String DELETE = "/delete/{id}";
    private final String FIND_BY_ID = "/{id}";
    private final String FIND_FILM = "/search/{title}";
    private final String FIND_BY_TITLE = "/title-search/{title}";
    private final String FIND_BY_TITLE_RATIO = "/title-ratio-search/{title}";
    private final String FIND_BY_PLOT_RATIO = "/plot-ratio-search/{title}";
    private final String UPDATE_COVER = "/cover-update";
    private final String GET_COVER = "/{id}/cover";
    private final String ADD_FAVORITE_SELECTION = "/{id}/fav-sel";
    private final String ADD_CUSTOM_SELECTION = "/{id}/cust-sel";



    private final FilmService filmService;
    private final SelectionService selectionService;


    @Operation(summary = "Get film by id")
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FilmDto> getFilmById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filmService.getFilmById(id));
    }


//    @Operation(summary = "Find film by title and plot")
//    @GetMapping(FIND_FILM)
//    public ResponseEntity<List<FilmDto>> getFilm(@PathVariable String title) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(filmService.getFilm(title));
//    }


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


    @Operation(summary = "Delete film")
    @PostMapping(SAVE_PATH)
    public ResponseEntity<Void> saveFilm(@RequestBody FilmDto filmDTO) {
        filmService.saveFilm(filmDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Delete film by id")
    @PostMapping(DELETE)
    public ResponseEntity<Void> deleteFilm(@RequestBody DeleteFilmDto filmDto) {
        filmService.deleteFilmById(filmDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Update film cover by id")
    @PostMapping(UPDATE_COVER)
    public ResponseEntity<Void> updateCover(@ModelAttribute UpdateFilmCoverDto filmCoverDto){
        filmService.updateFilmCover(filmCoverDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Get film cover by id")
    @GetMapping(GET_COVER)
    public ResponseEntity<ResponsePictureDto> getCover(@PathVariable long id){
        var cover = filmService.getFilmCover(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cover);
    }

    @Operation(summary = "Add film at favorite selection")
    @PostMapping(ADD_FAVORITE_SELECTION)
    public ResponseEntity<Void> addToFavoriteSel(@PathVariable long id, @RequestBody AddFilmInSelectionDTO filmInSelectionDTO){
        selectionService.addFilmToFavorite(filmInSelectionDTO.getUserId(), id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Add film at custom selection")
    @PostMapping(ADD_CUSTOM_SELECTION)
    public ResponseEntity<Void> addToCustomSel(@PathVariable long id, @RequestBody AddFilmInSelectionDTO filmInSelectionDTO){
        selectionService.addFilm(filmInSelectionDTO.getSelection_id(), id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
