package ru.stazaev.api.services;

import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;

import java.util.List;

public interface UserService {
    User getById(long id);
    User getByUsername(String username);
    List<FilmDto> getWillWatchSelection(String username);
    List<Selection> getCustomSelections(String username);
    boolean isAdministrator(String username);

    void addSelectionToUser(String username, long selectionId);

    void deleteSelectionFromUser(String username, long selectionId);
}
