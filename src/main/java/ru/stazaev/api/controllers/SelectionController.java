package ru.stazaev.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.controllers.intSwagger.ISelectionController;
import ru.stazaev.api.dto.request.SaveSelectionDto;
import ru.stazaev.api.dto.request.SaveSelectionDtoWithCover;
import ru.stazaev.api.dto.request.UpdateSelectionCoverDto;
import ru.stazaev.api.dto.response.SelectionDtoWithCover;
import ru.stazaev.api.services.SelectionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://movie-genie-131a7.web.app")
@RequestMapping("/api/selection")
public class SelectionController implements ISelectionController {

    private final SelectionService selectionService;
    private final String SAVE_PATH = "/save";
    private final String SAVE_WITH_COVER_PATH = "/save-with-cover";
    private final String FIND_BY_ID = "/{selection_id}";
    private final String FIND_ALL = "/selection";
    private final String FIND_BY_TAG = "/find-tag/{tag}";
    private final String DELETE_FILM_FROM_SELECTION = "/{selection_id}/delete/{film_id}";
    private final String ADD_FILM_TO_CUSTOM_SELECTION = "/{selection_id}/cust-sel/{film_id}";
    private final String ADD_FILM_TO_WILL_WATCH_SELECTION = "/will-watch-sel/{film_id}";
    private final String DELETE_FILM_FROM_WILL_WATCH_SELECTION = "/delete-will-watch/{film_id}";
    private final String DELETE_SELECTION_BY_ID = "/delete/{selection_id}";
    private final String DELETE_SELECTION_BY_TAG = "/delete-tag/{tag}";
    private final String UPDATE_COVER = "/cover-update";


    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<SelectionDtoWithCover> getSelection(@PathVariable("selection_id") long selectionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(selectionService.getById(selectionId));
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<SelectionDtoWithCover> getSelectionWithCover(@PathVariable("selection_id") long selectionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(selectionService.getByIdWithCover(selectionId));
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<SelectionDtoWithCover>> getAllSelections() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(selectionService.findAll());
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @GetMapping(FIND_BY_TAG)
    public ResponseEntity<SelectionDtoWithCover> getSelectionByTag(@PathVariable String tag) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(selectionService.getByTag(tag));
    }

//    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
//    @PostMapping(SAVE_PATH)
//    public ResponseEntity<Long> saveSelection(@RequestBody SaveSelectionDto selectionDTO, Authentication authentication) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(selectionService.saveNewSelection(selectionDTO, authentication));
//    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(SAVE_PATH)
    public ResponseEntity<Long> saveSelectionWithCover(@RequestBody SaveSelectionDtoWithCover selectionDTO, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(selectionService.saveNewSelectionWithCover(selectionDTO, authentication));
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(ADD_FILM_TO_WILL_WATCH_SELECTION)
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
            @PathVariable("selection_id") long selectionId,
            @PathVariable("film_id") long filmId,
            Authentication authentication) {
        selectionService.addFilmToCustomSelection(selectionId, filmId, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(DELETE_FILM_FROM_WILL_WATCH_SELECTION)
    public ResponseEntity<Void> deleteFilmFromWillWatchSelection(
            @PathVariable("film_id") long filmId,
            Authentication authentication) {
        selectionService.deleteFilmFromWillWatchSelection(filmId, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(DELETE_FILM_FROM_SELECTION)
    public ResponseEntity<Void> deleteFilmFromSelection(
            @PathVariable("film_id") long filmId,
            @PathVariable("selection_id") long selectionId,
            Authentication authentication) {
        selectionService.deleteFilmFromSelection(selectionId, filmId, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(DELETE_SELECTION_BY_ID)
    public ResponseEntity<Void> deleteSelectionById(
            @PathVariable("selection_id") long selectionId,
            Authentication authentication) {
        selectionService.deleteSelectionById(selectionId, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(DELETE_SELECTION_BY_TAG)
    public ResponseEntity<Void> deleteSelectionByTag(
            @PathVariable String tag,
            Authentication authentication) {
        selectionService.deleteSelectionByTag(tag, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @PostMapping(UPDATE_COVER)
    public ResponseEntity<Void> updateCover(
            @RequestBody UpdateSelectionCoverDto selectionCoverDto,
            Authentication authentication) {
        selectionService.updateSelectionCover(selectionCoverDto, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
