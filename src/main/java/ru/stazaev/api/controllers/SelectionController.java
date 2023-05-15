package ru.stazaev.api.controllers;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.request.DeleteSelectionDto;
import ru.stazaev.api.dto.request.SaveSelectionDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.services.SelectionService;

@RestController
@RequestMapping("/api/selection")
public class SelectionController {

    private final SelectionService selectionService;
    private final String SAVE_PATH = "/save";
    private final String ADD_FILM_PATH = "/{id}/add";
    private final String DELETE_FILM_PATH = "/{id}/delete";
    private final String DELETE_BY_ID = "/delete";
    private final String DELETE_BY_TAG = "/delete-tag/{tag}";


    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SelectionDto> getSelection(@PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(selectionService.getById(id));
    }

    @PostMapping(SAVE_PATH)
    public ResponseEntity<Void> saveSelection(@RequestBody SaveSelectionDto selectionDTO) {
        selectionService.save(selectionDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping(ADD_FILM_PATH)
    public ResponseEntity<Void> addFilmToSelectionById(@RequestParam("film") long filmId, @PathVariable long id) {
        selectionService.addFilm(id, filmId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(DELETE_FILM_PATH)
    public ResponseEntity<Void> deleteFilmFromSelection(@RequestParam("film") long filmId, @PathVariable long id) {
        selectionService.deleteFilm(id, filmId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping(DELETE_BY_ID)
    public ResponseEntity<Void> deleteSelectionById(@RequestBody DeleteSelectionDto selectionDto) {
        selectionService.deleteSelectionById(selectionDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(DELETE_BY_TAG)
    public ResponseEntity<Void> deleteSelectionByTag(@PathVariable String tag) {
        selectionService.deleteSelectionByTag(tag);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
