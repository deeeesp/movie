package ru.stazaev.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.request.UserLoginDto;
import ru.stazaev.api.dto.request.UserRegistrationDto;
import ru.stazaev.api.dto.response.JwtTokensDto;
import ru.stazaev.api.services.impl.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final String REGISTER = "/register";
    private final String LOGIN = "/login";

    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<JwtTokensDto> registerUser(
            @RequestBody UserRegistrationDto userRegistrationDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.registerUser(userRegistrationDto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<JwtTokensDto> loginUser(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.loginUser(userLoginDto));
    }
}
