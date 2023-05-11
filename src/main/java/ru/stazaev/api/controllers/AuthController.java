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

    private final AuthService authService;

    @PostMapping("/register")
    public JwtTokensDto registerUser(
            @RequestBody UserRegistrationDto userRegistrationDto) {
        return authService.registerUser(userRegistrationDto);
    }

    @PostMapping("/login")
    public JwtTokensDto loginUser(@RequestBody UserLoginDto userLoginDto) {
        return authService.loginUser(userLoginDto);
    }
}
