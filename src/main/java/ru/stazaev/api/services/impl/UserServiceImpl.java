package ru.stazaev.api.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.FilmDtoWithCover;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDtoWithCover;
import ru.stazaev.api.services.PictureStorage;
import ru.stazaev.api.services.SelectionService;
import ru.stazaev.api.services.UserService;
import ru.stazaev.store.entitys.*;
import ru.stazaev.store.repositories.FilmRepository;
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
    private final FilmRepository filmRepository;
    private final ModelMapper mapper;
    private final PictureStorage pictureStorage;



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
    public List<FilmDtoWithCover> getWillWatchSelection(String username) {
        List<FilmDtoWithCover> result = new ArrayList<>();
        var user = getByUsername(username);
        var films = user.getWillWatchFilms();
        for (Film film : films){
            var filmm = mapper.map(film, FilmDtoWithCover.class);
            filmm.setPictureId(film.getPicture().getId());
            result.add(filmm);
        }
        return result;
    }

    @Override
    public List<SelectionDtoWithCover> getCustomSelections(String username) {
        var user = getByUsername(username);
        return castListToDto(user.getSelections());
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
        if (!user.getSelections().contains(selection)) {
            user.getSelections().add(selection);
            userRepository.save(user);
        }
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

    private List<SelectionDtoWithCover> castListToDto(List<Selection> selections){
        List<SelectionDtoWithCover> dtos = new ArrayList<>();
        for (Selection selection : selections){
            dtos.add(castToDto(selection));
        }
        return dtos;
    }

    private SelectionDtoWithCover castToDto(Selection selection){
        var temp = mapper.map(selection, SelectionDtoWithCover.class);
        var coverId = selection.getPicture().getId();
        for (int i = 0; i < temp.getFilms().size(); i++) {

            long ind = temp.getFilms().get(i).getId();
            temp.getFilms().get(i).setPictureId(filmRepository.findById(ind).get().getPicture().getId());
        }
        temp.setPictureId(coverId);
        return temp;
    }
}
