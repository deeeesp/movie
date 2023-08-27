package ru.stazaev.api.services;

import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;

import java.util.List;

public interface UserService {
    User getById(long id);

    Selection getFavoriteSelection(long id);

    List<Selection> getCustomSelections(long id);

    List<Selection> getAllSelections(long id);
}
