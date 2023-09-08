package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.mock.web.MockMultipartFile;
import ru.stazaev.api.dto.request.*;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.api.services.SelectionService;
import ru.stazaev.api.services.PictureStorage;
import ru.stazaev.api.services.UserService;
import ru.stazaev.store.entitys.*;
import ru.stazaev.store.repositories.FilmRepository;
import ru.stazaev.store.repositories.PictureRepository;
import ru.stazaev.store.repositories.SelectionRepository;
import ru.stazaev.store.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Setter
public class SelectionServiceImpl implements SelectionService {
    private final String NO_SUCH_ELEMENT = "Не удалось найти элемент с таким id";
    private final String NOT_ENOUGH_RIGHT = "Недостаточно прав";
    private final String SAVE_STORAGE_COVER_ERROR = "Ошибка при загрузке изображения в облако";
    private final String DELETE_STORAGE_COVER_ERROR = "Ошибка при удалении изображения из облака";
    private final String GET_STORAGE_COVER_ERROR = "Ошибка при получении изображения из облака";


    private final SelectionRepository selectionRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final PictureStorage pictureStorage;
    private final ModelMapper mapper;
    private final UserService userService;
    private final FilmService filmService;


    @Override
    public SelectionDto getSelection() {

        return null;
    }

    @Override
    public SelectionDto getById(long selectionId) {
        var selection = getSelectionById(selectionId);
        return mapper.map(selection, SelectionDto.class);
    }

    @Override
    public List<SelectionDto> findAll() {
        var selections = selectionRepository.findAll();
        List<SelectionDto> dtos = new ArrayList<>();
        for (Selection selection : selections){
            dtos.add(mapper.map(selection, SelectionDto.class));
        }
        return dtos;
    }

    @Override
    public SelectionDto getByTag(String tag) {
        var selection = getSelectionByTag(tag);
        return mapper.map(selection, SelectionDto.class);
    }

    public void saveNewSelection(SaveSelectionDto selectionDTO) {
        Selection selection = mapper.map(selectionDTO, Selection.class);
        selection.setStatus(Status.ACTIVE);
        var user = userService.getById(selection.getOwner());
        user.getSelections().add(selection);
        userRepository.save(user);
    }


    @Override
    public void addFilmToCustomSelection(long selectionId, long filmId, String username) {
        var film = filmService.getById(filmId);
        var selection = getSelectionById(selectionId);
        if (isOwnerOrAdmin(username, selection)) {
            selection.getFilms().add(film);
            selectionRepository.save(selection);
        }
    }
    @Override
    public void addFilmToWillWatch(String username, long filmId) {
        var film = filmService.getById(filmId);
        var user = userService.getByUsername(username);
        user.getWillWatchFilms().add(film);
        userRepository.save(user);
    }

    @Override
    public void deleteFilmFromSelection(long selectionId, long filmId, String username) {
        var film = filmService.getById(filmId);
        var selection = getSelectionById(selectionId);
        if (isOwnerOrAdmin(username, selection)) {
            selection.getFilms().remove(film);
            selectionRepository.save(selection);
        }
    }

    @Override
    public void deleteFilmFromWillWatchSelection(long filmId, String username) {
        var film = filmService.getById(filmId);
        var user = userService.getByUsername(username);
        user.getWillWatchFilms().remove(film);
    }

    @Override
    public void deleteSelectionById(long selectionId, String username) {
        var selection = getSelectionById(selectionId);
        if (isOwnerOrAdmin(username, selection)) {
            selectionRepository.deleteById(selectionId);
        } else {
            throw new AccessDeniedException(NOT_ENOUGH_RIGHT);
        }
    }

    @Override
    public void deleteSelectionByTag(String tag, String username) {
        var selection = getSelectionByTag(tag);
        if (isOwnerOrAdmin(username, selection)) {
            selectionRepository.deleteByTag(tag);
        }else {
            throw new AccessDeniedException(NOT_ENOUGH_RIGHT);
        }
    }

    @Override
    public void updateSelectionCover(UpdateSelectionCoverDto selectionCoverDto, String username) {
        var selection = getSelectionById(selectionCoverDto.getSelectionId());
        if (isOwnerOrAdmin(username, selection)) {
            Picture newCover = Picture.builder()
                    .pictureType(selectionCoverDto.getPictureType())
                    .build();
            newCover = pictureRepository.save(newCover);

            String newCoverPath = pictureStorage.getSelectionCoverPath(newCover);
            try {
                pictureStorage.savePicture(newCoverPath, new MockMultipartFile(newCoverPath, selectionCoverDto.getPicture()));
            } catch (Exception e) {
                throw new RuntimeException(SAVE_STORAGE_COVER_ERROR);
            }

            deleteOldCower(selection.getPicture());

            selection.setPicture(newCover);
            selectionRepository.save(selection);
        } else {
            throw new AccessDeniedException(NOT_ENOUGH_RIGHT);
        }

    }

    @Override
    public ResponsePictureDto getSelectionCover(long selectionId) {
        var selection = getSelectionById(selectionId);
        Picture cover = selection.getPicture();
        if (cover != null) {
            String path = pictureStorage.getSelectionCoverPath(cover);
            byte[] coverData;
            try {
                coverData = pictureStorage.getPicture(path);
            } catch (Exception e) {
                throw new RuntimeException(GET_STORAGE_COVER_ERROR);
            }
            return ResponsePictureDto.builder()
                    .data(coverData)
                    .pictureType(cover.getPictureType())
                    .build();
        }
        return null;
    }

    @Override
    public void deleteSelectionCover(Long selectionId, String username) {
        var selection = getSelectionById(selectionId);
        if (isOwnerOrAdmin(username, selection)) {
            deleteOldCower(selection.getPicture());
        }
    }

    private void deleteOldCower(Picture oldCover) {
        if (oldCover != null) {
            String oldCowerPath = pictureStorage.getSelectionCoverPath(oldCover);
            try {
                pictureStorage.deletePicture(oldCowerPath);
            } catch (Exception e) {
                throw new RuntimeException(DELETE_STORAGE_COVER_ERROR);
            }
            pictureRepository.delete(oldCover);
        }
    }

    @Override
    public Selection getSelectionById(long selectionId){
        return selectionRepository.findById(selectionId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
    }

    public Selection getSelectionByTag(String tag){
        return selectionRepository.findByTag(tag)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
    }

    private boolean isOwnerOrAdmin(String username, Selection selection){
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        return (user.getRole().equals(Role.ADMIN) || (selection.getOwner() == user.getId()));
    }
}
