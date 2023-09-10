package ru.stazaev.api.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.FilmDtoWithCover;
import ru.stazaev.api.dto.response.ResponsePictureDto;
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
            filmm.setResponsePictureDto(getFilmCover(film.getId()));
            result.add(filmm);
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

    public ResponsePictureDto getFilmCover(long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(()->new NoSuchElementException("err"));
        Picture cover = film.getPicture();
        if (cover != null) {
            String coverPath = pictureStorage.getFilmCoverPath(cover);
            byte[] coverData = null;
            try {
                coverData = pictureStorage.getPicture(coverPath);
            } catch (Exception e) {
                System.out.println("e");
            }
            return ResponsePictureDto.builder()
                    .data(coverData)
                    .pictureType(cover.getPictureType())
                    .build();
        }
        return null;
    }

}
