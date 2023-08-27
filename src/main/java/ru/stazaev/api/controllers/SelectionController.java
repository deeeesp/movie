package ru.stazaev.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.request.DeleteSelectionDto;
import ru.stazaev.api.dto.request.SaveSelectionDto;
import ru.stazaev.api.dto.request.UpdateSelectionCoverDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.services.SelectionService;

@Tag(name = "Selection API", description = "Allows to find and edit selections")
@RestController
@RequestMapping("/api/selection")
public class SelectionController {

    private final SelectionService selectionService;
    private final String SAVE_PATH = "/save";
    private final String FIND_BY_ID = "/{id}";
    private final String FIND_BY_TAG = "/find-tag/{tag}";
//    private final String ADD_FILM_PATH = "/{id}/add";
    private final String DELETE_FILM_PATH = "/{id}/delete";
    private final String DELETE_BY_ID = "/delete";
    private final String DELETE_BY_TAG = "/delete-tag/{tag}";
    private final String UPDATE_COVER = "/cover-update";
    private final String GET_COVER = "/{id}/cover";


    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @Operation(summary = "Get selection by id")
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<SelectionDto> getSelection(@PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(selectionService.getById(id));
    }

    @Operation(summary = "Delete selection by tag")
    @GetMapping(FIND_BY_TAG)
    public ResponseEntity<SelectionDto> getSelectionByTag(@PathVariable String tag) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(selectionService.getByTag(tag));
    }

    @Operation(summary = "Save selection")
    @PostMapping(SAVE_PATH)
    public ResponseEntity<Void> saveSelection(@RequestBody SaveSelectionDto selectionDTO) {
        selectionService.save(selectionDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /*
    @Operation(summary = "Add film to selection")
    @PostMapping(ADD_FILM_PATH)
    public ResponseEntity<Void> addFilmToSelectionById(@RequestParam("film") long filmId, @PathVariable long id) {
        selectionService.addFilm(id, filmId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


     */
    @Operation(summary = "Delete film from selection by id")
    @PostMapping(DELETE_FILM_PATH)
    public ResponseEntity<Void> deleteFilmFromSelection(@RequestParam("film") long filmId, @PathVariable long id) {
        selectionService.deleteFilm(id, filmId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Delete selection by id")
    @PostMapping(DELETE_BY_ID)
    public ResponseEntity<Void> deleteSelectionById(@RequestBody DeleteSelectionDto selectionDto) {
        selectionService.deleteSelectionById(selectionDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Delete selection by tag")
    @PostMapping(DELETE_BY_TAG)
    public ResponseEntity<Void> deleteSelectionByTag(@PathVariable String tag) {
        selectionService.deleteSelectionByTag(tag);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Update selection cover by id")
    @PostMapping(UPDATE_COVER)
    public ResponseEntity<Void> updateCover(@ModelAttribute UpdateSelectionCoverDto selectionCoverDto){
        selectionService.updateSelectionCover(selectionCoverDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Get selection cover by id")
    @GetMapping(GET_COVER)
    public ResponseEntity<ResponsePictureDto> getCover(@PathVariable long id){
        var cover = selectionService.getSelectionCover(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cover);
    }
}
