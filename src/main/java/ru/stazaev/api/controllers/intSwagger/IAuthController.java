package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ru.stazaev.api.dto.request.UserLoginDto;
import ru.stazaev.api.dto.request.UserRegistrationDto;
import ru.stazaev.api.dto.response.JwtTokensDto;

@Tag(name = "Authentication API", description = "Allows to  register and login JWT tokens")
public interface IAuthController {

    @Operation(summary = "Провести регистрацию нового пользователя")
    ResponseEntity<JwtTokensDto> registerUser(
            @RequestBody(description = "Электронная почта, логин и пароль пользователя")
            UserRegistrationDto userRegistrationDto);

    @Operation(summary = "Войти в систему")
    ResponseEntity<JwtTokensDto> loginUser(
            @RequestBody(description = "Логин и пароль пользователя")
            UserLoginDto userLoginDto);

    @Operation(summary = "Обновить актуальность JWT токена")
    ResponseEntity<JwtTokensDto> refreshToken(
            @RequestBody(description = "Jwt токен, который надо обновить")
            String refreshToken);
}
