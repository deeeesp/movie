package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ru.stazaev.api.dto.request.SaveSelectionDto;
import ru.stazaev.api.dto.request.UpdateSelectionCoverDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;

@Tag(name = "Selection API", description = "Allows to find and edit selections")
public interface ISelectionController{
    @Operation(summary = "Получить подборку по id")
    ResponseEntity<SelectionDto> getSelection(@Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId);

    @Operation(summary = "Получить подборку по тегу")
    ResponseEntity<SelectionDto> getSelectionByTag(@Parameter(description = "Тег подбоки, заданный пользователем", example = "my-new") String tag);

    @Operation(summary = "Сохранить новую подборку")
    ResponseEntity<Void> saveSelection(@RequestBody(description = "") SaveSelectionDto selectionDTO);

    @Operation(summary = "Добавить фильм в пользовательскую подбрку")
    ResponseEntity<Void> addToCustomSel(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            Authentication authentication);

    @Operation(summary = "Удалить фильм из подборки")
    ResponseEntity<Void> deleteFilmFromSelection(
            @Parameter(name = "film_id", description = "Идентификатор фильма", example = "1") long filmId,
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);

    @Operation(summary = "Удалить подборку по id")
    ResponseEntity<Void> deleteSelectionById(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);

    @Operation(summary = "Удалить подборку по тегу")
    ResponseEntity<Void> deleteSelectionByTag(
            @Parameter(description = "Тег подбоки, заданный пользователем", example = "my-new") String tag,
            Authentication authentication);

    @Operation(summary = "Загрузить новую обложку для подборки")
    ResponseEntity<Void> updateCover(
            @RequestBody(description = "") UpdateSelectionCoverDto selectionCoverDto,
            Authentication authentication);

    @Operation(summary = "Получить обложку подборки")
    ResponseEntity<ResponsePictureDto> getCover(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long id);
}
