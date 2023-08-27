package ru.stazaev.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/")
    public ResponseEntity<String> getMainPage() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Server is working");

    }
}
