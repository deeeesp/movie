package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.mock.web.MockMultipartFile;
import ru.stazaev.api.dto.request.*;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.dto.response.SelectionDtoWithCover;
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
    public SelectionDtoWithCover getById(long selectionId) {
        var sel = getSelectionById(selectionId);
        return castToDto(sel);
    }

    @Override
    public SelectionDtoWithCover getByIdWithCover(long selectionId) {
        var sel = getSelectionById(selectionId);
        return castToDto(sel);
    }

    @Override
    public List<SelectionDtoWithCover> findAll() {
        var selections = selectionRepository.findAll();
        return castListToDto(selections);
    }

    @Override
    public SelectionDtoWithCover getByTag(String tag) {
        var selection = getSelectionByTag(tag);
        var result = mapper.map(selection, SelectionDtoWithCover.class);
        result.setPictureId(selection.getPicture().getId());
        return result;
    }

    public Long saveNewSelection(SaveSelectionDto selectionDTO, Authentication authentication) {
        var films = selectionDTO.getFilms();
        Selection selection = mapper.map(selectionDTO, Selection.class);
        selection.getFilms().clear();
        for (int i = 0; i < films.size(); i++) {
            var film = filmRepository.findByTitle(films.get(i).getTitle());
            film.ifPresent(filmList -> selection.getFilms().add(filmList.get(0)));
        }
        selection.setStatus(Status.ACTIVE);
        var picture = pictureRepository.findById(0L).orElseThrow(()-> new NoSuchElementException("e"));
        selection.setPicture(picture);
        var user = userService.getByUsername(authentication.getName());
        selection.setOwner(user.getId());
        user.getSelections().add(selection);
        user = userRepository.save(user);
        var l = user.getSelections().indexOf(selection);
        return user.getSelections().get(l).getId();
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
        userRepository.save(user);
    }

    @Override
    public void deleteSelectionById(long selectionId, String username) {
        var selection = getSelectionById(selectionId);
        selection.getFilms().clear();
        selection = selectionRepository.save(selection);
        if (selection.getPicture().getId() == 0) {
            if (isOwnerOrAdmin(username, selection)) {
                selectionRepository.deleteById(selectionId);
                pictureRepository.save(new Picture(0, PictureType.JPEG));
            } else {
                throw new AccessDeniedException(NOT_ENOUGH_RIGHT);
            }
        } else {
            if (isOwnerOrAdmin(username, selection)) {
                selectionRepository.deleteById(selectionId);
            } else {
                throw new AccessDeniedException(NOT_ENOUGH_RIGHT);
            }
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

//            deleteOldCower(selection.getPicture());

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
            byte[] coverData = null;
            try {
                coverData = pictureStorage.getPicture(path);
            } catch (Exception e) {
                System.out.println(GET_STORAGE_COVER_ERROR);
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

    public List<SelectionDtoWithCover> castListToDto(List<Selection> selections) {
        List<SelectionDtoWithCover> dtos = new ArrayList<>();
        for (Selection selection : selections) {
            dtos.add(castToDto(selection));
        }
        return dtos;
    }

    public SelectionDtoWithCover castToDto(Selection selection) {
        var coverId = selection.getPicture().getId();
        var temp = mapper.map(selection, SelectionDtoWithCover.class);
        for (int i = 0; i < temp.getFilms().size(); i++) {
            long ind = temp.getFilms().get(i).getId();
//            temp.getFilms().get(i).setPictureId(filmService.getFilmCoverId(ind));
        }
        temp.setPictureId(coverId);
        return temp;
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
