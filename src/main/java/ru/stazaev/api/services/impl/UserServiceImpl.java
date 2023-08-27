package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stazaev.api.services.UserService;
import ru.stazaev.store.entitys.Role;
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
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new NoSuchElementException("Пользователь с таким именем не найден"));
    }

    @Override
    public Selection getFavoriteSelection(String username) {
        return getByUsername(username).getFavoriteSelection();
    }

    @Override
    public List<Selection> getCustomSelections(String username) {
        return getByUsername(username).getSelections();
    }

    @Override
    public List<Selection> getAllSelections(String username) {
        var user = getByUsername(username);
        List<Selection> selections = user.getSelections();
        selections.add(user.getFavoriteSelection());
        return selections;
    }

    @Override
    public boolean isAdministrator(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с таким именем не найден"));
        return user.getRole().equals(Role.ADMIN);
    }
}
