package ru.stazaev.api.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.services.SelectionService;
import ru.stazaev.api.services.UserService;
import ru.stazaev.store.entitys.Film;
import ru.stazaev.store.entitys.Role;
import ru.stazaev.store.entitys.Selection;
import ru.stazaev.store.entitys.User;
import ru.stazaev.store.repositories.SelectionRepository;
import ru.stazaev.store.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SelectionRepository selectionRepository;
    private final ModelMapper mapper;


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
    public List<FilmDto> getWillWatchSelection(String username) {
        List<FilmDto> result = new ArrayList<>();
        var user = getByUsername(username);
        var films = user.getWillWatchFilms();
        for (Film film : films){
            result.add(mapper.map(film, FilmDto.class));
        }
        return result;
    }

    @Override
    public List<Selection> getCustomSelections(String username) {
        return getByUsername(username).getSelections();
    }

    @Override
    public boolean isAdministrator(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с таким именем не найден"));
        return user.getRole().equals(Role.ADMIN);
    }

    @Override
    public void addSelectionToUser(String username, long selectionId) {
        var user = getByUsername(username);
        var selection = selectionRepository.findById(selectionId)
                .orElseThrow(() -> new NoSuchElementException("Подборка с таким id не найдена"));
        user.getSelections().add(selection);
        userRepository.save(user);
    }

    @Override
    public void deleteSelectionFromUser(String username, long selectionId) {
        var user = getByUsername(username);
        var selection = selectionRepository.findById(selectionId)
                .orElseThrow(() -> new NoSuchElementException("Подборка с таким id не найдена"));
        if (user.getSelections().remove(selection)) {
            userRepository.save(user);
        } else {
            throw new RuntimeException("Не удалось удать подброку");
        }

    }
}
