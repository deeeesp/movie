package ru.stazaev.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.controllers.intSwagger.IUserController;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.services.UserService;
import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://movie-genie-131a7.web.app/")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController implements IUserController {
    private final String FIND_BY_ID = "/{user_id}";
    private final String GET_FAV_SELECTION = "/fav-sel";
    private final String GET_WILL_WATCH_SELECTION = "/will-watch";
    private final String GET_CUSTOM_SELECTION = "/cust-sel";
    private final String GET_ALL_SELECTIONS = "/all-sel";
    private final String ADD_SELECTION_TO_USER = "/add/{selection_id}";
    private final String DELETE_SELECTION_FROM_USER = "/delete/{selection_id}";


    private final UserService userService;

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<String> getUser(@PathVariable("user_id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getById(id).getRole().toString());
    }

    @GetMapping(GET_WILL_WATCH_SELECTION)
    public ResponseEntity<List<FilmDto>> getUserWillWatchSelection(
            Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getWillWatchSelection(authentication.getName()));
    }

    @GetMapping(GET_CUSTOM_SELECTION)
    public ResponseEntity<List<Selection>> getUserSelections(
            Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getCustomSelections(authentication.getName()));
    }

    @PostMapping(ADD_SELECTION_TO_USER)
    public ResponseEntity<Void> addSelectionToUser(
            @PathVariable("selection_id") long selectionId,
            Authentication authentication) {
        userService.addSelectionToUser(authentication.getName(), selectionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(DELETE_SELECTION_FROM_USER)
    public ResponseEntity<Void> deleteSelectionFromUser(
            @PathVariable("selection_id") long selectionId,
            Authentication authentication) {
        userService.deleteSelectionFromUser(authentication.getName(), selectionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
