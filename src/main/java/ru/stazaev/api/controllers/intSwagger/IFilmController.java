package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.*;

import java.util.List;

@Tag(name = "Film API", description = "Allows to find films")
public interface IFilmController {
    @Operation(summary = "Получить фильм по id",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм не найден"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "406", description = "Облачное хранилище не работает"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Фильм найден")
    ResponseEntity<FilmDtoWithCover> getFilmById(@Parameter(name = "user_id", description = "Идентификатор пользователя", example = "1") Long id);

    @Operation(summary = "Найти фильм по названию или содержанию",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм не найден"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Фильм найден")
    ResponseEntity<FilmSearchDto> getFilm(@Parameter(name = "title", description = "Название фильма", example = "Начало") String title);

    @Operation(summary = "Найти все фильмы",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильмы не найдены"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Фильм найден")
    ResponseEntity<List<FilmDtoWithCover>> getFilms();

    @Operation(summary = "Загрузить новый фильм",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав")
            })
    @ApiResponse(responseCode = "201", description = "Фильм сохранен")
    ResponseEntity<Void> saveFilm(
            @RequestBody(description = "") FilmDto filmDTO,
            Authentication authentication);

    @Operation(summary = "Удалить фильм",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "403", description = "Недостаточно прав")
            })
    @ApiResponse(responseCode = "200", description = "Фильм удален")
    ResponseEntity<Void> deleteFilm(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long id,
            Authentication authentication);

    @Operation(summary = "Загрузить новую обложку для фильма",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм не найден"
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
    @ApiResponse(responseCode = "200", description = "Обложка фильма обновлена")
    ResponseEntity<Void> updateCover(
            @RequestBody(description = "") UpdateFilmCoverDto filmCoverDto,
            Authentication authentication);

    @Operation(summary = "Получить обложку фильма",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм не найден"
                    ),
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "406", description = "Облачное хранилище не работает"
                    )
            })
    @ApiResponse(responseCode = "200", description = "Обложка фильма получена")
    ResponseEntity<ResponsePictureDto> getCover(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1")long id);

    @Operation(summary = "Добавить фильм в подбрку - буду смотреть",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                            },
                            responseCode = "404", description = "Фильм не найден"
                    )
            })
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
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);
}
