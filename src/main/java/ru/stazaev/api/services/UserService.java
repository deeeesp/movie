package ru.stazaev.api.services;

import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;

import java.util.List;

public interface UserService {
    User getById(long id);

    User getByUsername(String username);

    Selection getFavoriteSelection(String username);
    Selection getWillWatchSelection(String username);

    List<Selection> getCustomSelections(String username);

    List<Selection> getAllSelections(String username);

    boolean isAdministrator(String username);

    void addSelectionToUser(String username, long selectionId);

    void deleteSelectionFromUser(String username, long selectionId);
}
