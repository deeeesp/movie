package ru.stazaev.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stazaev.api.services.BaseService;
import ru.stazaev.store.repositories.UserRepository;


@RestController
public class ApplicationController {
    private static final String MAIN_PAGE = "/api/";

    @GetMapping("/")
    public ResponseEntity<String> getMainPage() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Server is working");

    }
}
