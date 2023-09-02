package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;

import java.util.List;

public interface IUserController {
    @Operation(summary = "Найти пользователя по id")
    ResponseEntity<String> getUser(@Parameter(name = "user_id", description = "Идентификатор пользователя", example = "1") Long id);

    @Operation(summary = "Получить подборку 'Мое любимое'")
    ResponseEntity<Selection> getUserFavoriteSelection(
            Authentication authentication);

    @Operation(summary = "Получить все кастомные пользовательские подборки")
    ResponseEntity<List<Selection>> getUserCustomSelections(
            Authentication authentication);

    @Operation(summary = "Получить все пользовательские подборки")
    ResponseEntity<List<Selection>> getUserAllSelections(
            Authentication authentication);

    @Operation(summary = "Добавить пользовательскую подборку пользователю")
    ResponseEntity<Void> addSelectionToUser(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);

    @Operation(summary = "Удалить пользовательскую подборку у пользователя")
    ResponseEntity<Void> deleteSelectionFromUser(
            @Parameter(name = "selection_id", description = "Идентификатор подборки", example = "1") long selectionId,
            Authentication authentication);
}
