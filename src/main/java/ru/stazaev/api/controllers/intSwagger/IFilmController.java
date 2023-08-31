package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.FilmSearchDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;

@Tag(name = "Film API", description = "Allows to find films")
public interface IFilmController {
    @Operation(summary = "Получить фильм по id")
    ResponseEntity<FilmDto> getFilmById(@Parameter(name = "user_id", description = "Идентификатор пользователя", example = "1") Long id);

    @Operation(summary = "Найти фильм по названию или содержанию")
    ResponseEntity<FilmSearchDto> getFilm(@Parameter(name = "title", description = "Название фильма", example = "Начало") String title);

    @Operation(summary = "Загрузить новый фильм")
    ResponseEntity<Void> saveFilm(
            @RequestBody(description = "") FilmDto filmDTO,
            Authentication authentication);

    @Operation(summary = "Удалить фильм")
    ResponseEntity<Void> deleteFilm(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long id,
            Authentication authentication);

    @Operation(summary = "Загрузить новую обложку для фильма")
    ResponseEntity<Void> updateCover(
            @RequestBody(description = "") UpdateFilmCoverDto filmCoverDto,
            Authentication authentication);

    @Operation(summary = "Получить обложку фильма")
    ResponseEntity<ResponsePictureDto> getCover(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1")long id);

    @Operation(summary = "Добавить фильм в подбрку - мне нравится")
    ResponseEntity<Void> addToFavoriteSel(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            Authentication authentication);

    @Operation(summary = "Добавить фильм в пользовательскую подбрку")
    ResponseEntity<Void> addToCustomSel(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);
}
