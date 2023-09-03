package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import ru.stazaev.api.dto.response.ApiErrorResponse;
import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;

import java.util.List;

public interface IUserController {
    @Operation(summary = "Найти пользователя по id",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Пользователь не найден"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    ResponseEntity<String> getUser(@Parameter(name = "user_id", description = "Идентификатор пользователя", example = "1") Long id);

    @Operation(summary = "Получить подборку 'Мое любимое'",
            responses = {
            @ApiResponse(
                    content = {
                            @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    },
                    responseCode = "404", description = "Подборка не найдена"
            )
    })
    @ApiResponse(responseCode = "200", description = "Подборка найдена")
    ResponseEntity<Selection> getUserFavoriteSelection(
            Authentication authentication);

    @Operation(summary = "Получить все кастомные пользовательские подборки",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборки не найдены"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Подборки найдены")
    ResponseEntity<List<Selection>> getUserCustomSelections(
            Authentication authentication);

    @Operation(summary = "Получить все пользовательские подборки",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборки не найдены"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Подборки найдены")
    ResponseEntity<List<Selection>> getUserAllSelections(
            Authentication authentication);

    @Operation(summary = "Добавить пользовательскую подборку пользователю",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборка не найдена")
            })
    @ApiResponse(responseCode = "200", description = "Подборка добавлена")
    ResponseEntity<Void> addSelectionToUser(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);

    @Operation(summary = "Удалить пользовательскую подборку у пользователя",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборка не найдена")
            })
    @ApiResponse(responseCode = "200", description = "Подборка удалена")
    ResponseEntity<Void> deleteSelectionFromUser(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);
}
