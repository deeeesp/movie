package ru.stazaev.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.SelectionDTO;
import ru.stazaev.api.services.SelectionService;

@RestController
@RequestMapping("/api/selection")
public class SelectionController {

    private final SelectionService selectionService;
    private final String SAVE_PATH = "/save";
    private final String ADD_FILM_PATH = "/{id}/add";
    private final String DELETE_FILM_PATH = "/{id}/delete";
    private final String DELETE_BY_ID = "/delete/{id}";
    private final String DELETE_BY_TAG = "/delete-tag/{tag}";


    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @GetMapping("/{id}")
    public SelectionDTO getSelection(@PathVariable long id){
        return selectionService.getById(id);
    }

    @PostMapping(SAVE_PATH)
    public String saveSelection(@RequestBody SelectionDTO selectionDTO){
        selectionService.save(selectionDTO);
        return "success";
    }

    @PostMapping(ADD_FILM_PATH)
    public String addFilmToSelection(@RequestParam("film") long filmId, @PathVariable long id){
        selectionService.addFilm(id, filmId);
        return "success";
    }

    @PostMapping(DELETE_FILM_PATH)
    public String deleteFilmFromSelection(@RequestParam("film") long filmId, @PathVariable long id){
        selectionService.deleteFilm(id, filmId);
        return "success";
    }

    @PostMapping(DELETE_BY_ID)
    public String addFilmToSelection(@PathVariable long id){
        selectionService.deleteSelectionById(id);
        return "success";
    }

    @PostMapping(DELETE_BY_TAG)
    public String addFilmToSelection(@PathVariable String tag){
        selectionService.deleteSelectionByTag(tag);
        return "success";
    }

}
