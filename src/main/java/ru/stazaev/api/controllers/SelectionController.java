package ru.stazaev.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.SelectionDTO;
import ru.stazaev.api.services.SelectionService;

@RestController
@RequestMapping("/api/selection")
public class SelectionController {

    private final SelectionService selectionService;
    private final String SAVE_PATH = "/save";
    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @GetMapping("/{id}")
    public SelectionDTO getSelection(@PathVariable long id){
        return selectionService.getById(id);
    }

    @PostMapping(SAVE_PATH)
    public String saveSelection(@RequestBody SelectionDTO selectionDTO){
        System.out.println(selectionDTO);
        selectionService.save(selectionDTO);
        return "success";
    }
}
