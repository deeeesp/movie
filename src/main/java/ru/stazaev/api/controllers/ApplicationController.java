package ru.stazaev.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.api.dto.MainPageRequestDTO;
import ru.stazaev.api.services.BaseService;

import java.util.List;

@RestController
public class ApplicationController {
    private final BaseService baseService;
    private static final String MAIN_PAGE = "/api/";

    public ApplicationController(BaseService baseService) {
        this.baseService = baseService;
    }

    @GetMapping(MAIN_PAGE)
    public MainPageRequestDTO getMainPage(){
        return baseService.get();
    }
}
