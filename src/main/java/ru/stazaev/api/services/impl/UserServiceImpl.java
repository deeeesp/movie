package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stazaev.api.services.UserService;
import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;
import ru.stazaev.store.repositories.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getById(long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Пользователь с таким id не найден"));
    }

    @Override
    public Selection getFavoriteSelection(long id) {
        return getById(id).getFavoriteSelection();
    }

    @Override
    public List<Selection> getCustomSelections(long id) {
        return getById(id).getSelections();
    }

    @Override
    public List<Selection> getAllSelections(long id) {
        var user = getById(id);
        List<Selection> selections = user.getSelections();
        selections.add(user.getFavoriteSelection());
        return selections;
    }
}
