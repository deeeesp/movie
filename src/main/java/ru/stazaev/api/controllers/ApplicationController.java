package ru.stazaev.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stazaev.api.dto.request.MainPageRequestDto;
import ru.stazaev.api.services.BaseService;


@RestController
public class ApplicationController {
    private final BaseService baseService;
    private static final String MAIN_PAGE = "/api/";

    public ApplicationController(BaseService baseService) {
        this.baseService = baseService;
    }

    @GetMapping(MAIN_PAGE)
    public MainPageRequestDto getMainPage(){
        return baseService.get();
    }
}
