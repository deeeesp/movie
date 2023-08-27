package ru.stazaev.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.services.UserService;
import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final String FIND_BY_ID = "/{id}";
    private final String GET_FAV_SELECTION = "/fav-sel";
    private final String GET_CUSTOM_SELECTION = "/cust-sel";
    private final String GET_ALL_SELECTIONS = "/all-sel";


    private final UserService userService;

    @Operation(summary = "Get user by id")
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getById(id));
    }

    @Operation(summary = "Get users favorite selection")
    @GetMapping(GET_FAV_SELECTION)
    public ResponseEntity<Selection> getUserFavoriteSelection(
            Authentication authentication){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getFavoriteSelection(authentication.getName()));
    }

    @Operation(summary = "Get users custom selections")
    @GetMapping(GET_CUSTOM_SELECTION)
    public ResponseEntity<List<Selection>> getUserCustomSelections(
            Authentication authentication){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getCustomSelections(authentication.getName()));
    }

    @Operation(summary = "Get users all selections")
    @GetMapping(GET_ALL_SELECTIONS)
    public ResponseEntity<List<Selection>> getUserAllSelections(
            Authentication authentication){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllSelections(authentication.getName()));
    }
}
