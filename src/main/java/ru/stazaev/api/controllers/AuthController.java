package ru.stazaev.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.controllers.intSwagger.IAuthController;
import ru.stazaev.api.dto.request.UserLoginDto;
import ru.stazaev.api.dto.request.UserRegistrationDto;
import ru.stazaev.api.dto.response.JwtTokensDto;
import ru.stazaev.api.services.impl.AuthService;


@RequiredArgsConstructor
@CrossOrigin(origins = "https://movie-genie-131a7.web.app")
@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {
    private final String REGISTER = "/register";
    private final String LOGIN = "/login";
    private final String REFRESH = "/refresh";


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

    @PostMapping(REFRESH)
    public ResponseEntity<JwtTokensDto> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.refreshToken(refreshToken));
    }
}
