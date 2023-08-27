package ru.stazaev.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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
    private final String FIND_BY_ID = "/{selectionId}";
    private final String FIND_BY_TAG = "/find-tag/{tag}";
    private final String DELETE_FILM_FROM_SELECTION = "/{selectionId}/delete/{filmId}";
    private final String DELETE_SELECTION_BY_ID = "/delete/{selectionId}";
    private final String DELETE_SELECTION_BY_TAG = "/delete-tag/{tag}";
    private final String UPDATE_COVER = "/cover-update";
    private final String GET_COVER = "/{id}/cover";


    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @Operation(summary = "Get selection by id")
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<SelectionDto> getSelection(@PathVariable long selectionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(selectionService.getById(selectionId));
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
        selectionService.saveNewSelection(selectionDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @Operation(summary = "Delete film from selection by id")
    @PostMapping(DELETE_FILM_FROM_SELECTION)
    public ResponseEntity<Void> deleteFilmFromSelection(
            @PathVariable long filmId,
            @PathVariable long selectionId,
            Authentication authentication) {
        selectionService.deleteFilmFromSelection(selectionId, filmId, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Delete selection by id")
    @PostMapping(DELETE_SELECTION_BY_ID)
    public ResponseEntity<Void> deleteSelectionById(
            @PathVariable long selectionId,
            Authentication authentication
    ) {
        selectionService.deleteSelectionById(selectionId, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Delete selection by tag")
    @PostMapping(DELETE_SELECTION_BY_TAG)
    public ResponseEntity<Void> deleteSelectionByTag(
            @PathVariable String tag,
            Authentication authentication) {
        selectionService.deleteSelectionByTag(tag, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Update selection cover by id")
    @PostMapping(UPDATE_COVER)
    public ResponseEntity<Void> updateCover(
            @ModelAttribute UpdateSelectionCoverDto selectionCoverDto,
            Authentication authentication) {
        selectionService.updateSelectionCover(selectionCoverDto, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Get selection cover by id")
    @GetMapping(GET_COVER)
    public ResponseEntity<ResponsePictureDto> getCover(@PathVariable long id) {
        var cover = selectionService.getSelectionCover(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cover);
    }
}
