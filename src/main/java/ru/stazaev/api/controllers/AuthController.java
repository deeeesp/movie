package ru.stazaev.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.dto.request.UserLoginDto;
import ru.stazaev.api.dto.request.UserRegistrationDto;
import ru.stazaev.api.dto.response.JwtTokensDto;
import ru.stazaev.api.services.impl.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;



@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Allows to  register and login JWT tokens")
public class AuthController {
    private final String REGISTER = "/register";
    private final String LOGIN = "/login";

    private final AuthService authService;

    @Operation(summary = "Registers new user and returns corresponding JWT tokens")
    @PostMapping(REGISTER)
    public ResponseEntity<JwtTokensDto> registerUser(
            @RequestBody UserRegistrationDto userRegistrationDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.registerUser(userRegistrationDto));
    }

    @Operation(summary = "Returns JWT tokens if OK")
    @PostMapping(LOGIN)
    public ResponseEntity<JwtTokensDto> loginUser(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.loginUser(userLoginDto));
    }
}
