package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ru.stazaev.api.dto.request.SaveSelectionDto;
import ru.stazaev.api.dto.request.UpdateSelectionCoverDto;
import ru.stazaev.api.dto.response.ApiErrorResponse;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.dto.response.SelectionDtoWithCover;
import ru.stazaev.store.entitys.Selection;

import java.util.List;

@Tag(name = "Selection API", description = "Allows to find and edit selections")
public interface ISelectionController {
    @Operation(summary = "Получить подборку по id",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборка не найдена"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Подборка найдена")
    ResponseEntity<SelectionDtoWithCover> getSelection(@Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId);

    @Operation(summary = "Получить подборку по id вместе с обложкой",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборка не найдена"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "406", description = "Облачное хранилище не работает"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Подборка найдена")
    ResponseEntity<SelectionDtoWithCover> getSelectionWithCover(@Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId);

    @Operation(summary = "Получить все подборки",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборки не найдены"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Подборки найдены")
    ResponseEntity<List<SelectionDtoWithCover>> getAllSelections();

    @Operation(summary = "Получить подборку по тегу",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборка не найдена"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Подборка найдена")
    ResponseEntity<SelectionDtoWithCover> getSelectionByTag(@Parameter(description = "Тег подбоки, заданный пользователем", example = "my-new") String tag);

    @Operation(summary = "Сохранить новую подборку",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав")
            })
    @ApiResponse(responseCode = "201", description = "Подборка сохранен")
    ResponseEntity<Void> saveSelection(@RequestBody(description = "") SaveSelectionDto selectionDTO);

    @Operation(summary = "Добавить фильм в подбрку 'Буду смотреть'",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм или подборка не найдены"
                    )})
    @ApiResponse(responseCode = "200", description = "Фильм добален")
    ResponseEntity<Void> addToWillWatchSel(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            Authentication authentication);

    @Operation(summary = "Добавить фильм в пользовательскую подбрку",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм или подборка не найдены"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав")
            })
    @ApiResponse(responseCode = "200", description = "Фильм добален")
    ResponseEntity<Void> addToCustomSel(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            Authentication authentication);

    @Operation(summary = "Удалить фильм из подборки",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм или подборка не найдены"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав")
            })
    @ApiResponse(responseCode = "200", description = "Фильм удален")
    ResponseEntity<Void> deleteFilmFromSelection(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);

    @Operation(summary = "Удалить фильм из подборки 'Буду смотреть'",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм или подборка не найдены"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав")
            })
    @ApiResponse(responseCode = "200", description = "Фильм удален")
    ResponseEntity<Void> deleteFilmFromWillWatchSelection(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            Authentication authentication);

    @Operation(summary = "Удалить подборку по id",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав")
            })
    @ApiResponse(responseCode = "200", description = "Подборка удалена")
    ResponseEntity<Void> deleteSelectionById(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);

    @Operation(summary = "Удалить подборку по тегу",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав")
            })
    @ApiResponse(responseCode = "200", description = "Подборка удалена")
    ResponseEntity<Void> deleteSelectionByTag(
            @Parameter(description = "Тег подбоки, заданный пользователем", example = "my-new") String tag,
            Authentication authentication);

    @Operation(summary = "Загрузить новую обложку для подборки",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборка не найдена"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "406", description = "Облачное хранилище не работает"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Обложка подборки обновлена")
    ResponseEntity<Void> updateCover(
            @RequestBody(description = "") UpdateSelectionCoverDto selectionCoverDto,
            Authentication authentication);

    @Operation(summary = "Получить обложку подборки",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Подборка не найдена"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "406", description = "Облачное хранилище не работает"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Обложка подборки получена")
    ResponseEntity<ResponsePictureDto> getCover(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long id);
}
