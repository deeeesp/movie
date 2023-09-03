package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ru.stazaev.api.dto.request.UserLoginDto;
import ru.stazaev.api.dto.request.UserRegistrationDto;
import ru.stazaev.api.dto.response.ApiErrorResponse;
import ru.stazaev.api.dto.response.JwtTokensDto;

@Tag(name = "Authentication API", description = "Allows to  register and login JWT tokens")
public interface IAuthController {

    @Operation(summary = "Провести регистрацию нового пользователя",
            responses = {

                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "409", description = "Пользователь с такими данными уже существует"
                    )
            })
    @ApiResponse(responseCode = "201")
    ResponseEntity<JwtTokensDto> registerUser(
            @RequestBody(description = "Электронная почта, логин и пароль пользователя")
            UserRegistrationDto userRegistrationDto);

    @Operation(summary = "Войти в систему",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Пользователь с такими данными заблокирован"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "409", description = "Неверные данные"
                    )
            })
    @ApiResponse(responseCode = "200")
    ResponseEntity<JwtTokensDto> loginUser(
            @RequestBody(description = "Логин и пароль пользователя")
            UserLoginDto userLoginDto);

    @Operation(summary = "Обновить актуальность JWT токена",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Пользователь с такими данными заблокирован"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "409", description = "Неверные данные"
                    )
            })
    @ApiResponse(responseCode = "200")
    ResponseEntity<JwtTokensDto> refreshToken(
            @Parameter(name = "jwt token", description = "Jwt токен, который надо обновить", example = "AA432QSjytkZJRgABAQAAAQAB...j4cjwAP/9k=")
            String refreshToken);
}
