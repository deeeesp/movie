package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.request.*;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.mappers.SelectionDTOMapper;
import ru.stazaev.api.services.PictureStorage;
import ru.stazaev.store.entitys.Picture;
import ru.stazaev.store.repositories.FilmRepository;
import ru.stazaev.store.repositories.PictureRepository;
import ru.stazaev.store.repositories.SelectionRepository;
import ru.stazaev.store.repositories.UserRepository;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class SelectionServiceImpl implements ru.stazaev.api.services.SelectionService {
    private final String NO_SUCH_ELEMENT = "Не удалось найти элемент с таким id";
    private final String NOT_ENOUGH_RIGHT = "Недостаточно прав";
    private final String SAVE_STORAGE_COVER_ERROR = "Ошибка при загрузке изображения в облако";
    private final String DELETE_STORAGE_COVER_ERROR = "Ошибка при удалении изображения из облака";
    private final String GET_STORAGE_COVER_ERROR = "Ошибка при получении изображения из облака";


    private final SelectionRepository selectionRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final SelectionDTOMapper mapper;
    private final PictureRepository pictureRepository;
    private final PictureStorage pictureStorage;


    @Override
    public SelectionDto getSelection() {
        return null;
    }

    @Override
    public SelectionDto getById(long id) {
        var findById = selectionRepository.findById(id);
        return findById.map(mapper::entityToDto)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
    }

    public void save(SaveSelectionDto selectionDTO) {
        var selection = mapper.DTOToEntity(selectionDTO);
        selectionRepository.save(selection);
    }


    @Override
    public void addFilm(long id, long filmId) {
        var film = filmRepository.findById(filmId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        var selection = selectionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));

        selection.getFilms().add(film);
        selectionRepository.save(selection);
    }

    @Override
    public void deleteFilm(long id, long filmId) {
        var film = filmRepository.findById(filmId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        var selection = selectionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));

        selection.getFilms().remove(film);
        selectionRepository.save(selection);
    }

    @Override
    public void deleteSelectionById(DeleteSelectionDto selectionDto) {
        var selection = selectionRepository.findById(selectionDto.getSelectionId())
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));

        if (selection.getOwner() == selectionDto.getUserId()) {
            selectionRepository.deleteById(selectionDto.getSelectionId());
        } else {
            throw new RuntimeException(NOT_ENOUGH_RIGHT);
        }
    }

    @Override
    public void deleteSelectionByTag(String tag) {
        selectionRepository.deleteByTag(tag);
    }

    @Override
    public void updateSelectionCover(UpdateSelectionCoverDto filmCoverDto) {
        var selection = selectionRepository.findById(filmCoverDto.getSelectionId())
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        var user = userRepository.findById(filmCoverDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        if (selection.getOwner() == user.getId()) {
            Picture newCover = Picture.builder()
                    .pictureType(filmCoverDto.getPictureType())
                    .build();
            newCover = pictureRepository.save(newCover);

            String newCoverPath = pictureStorage.getSelectionCoverPath(newCover);
            try {
                pictureStorage.savePicture(newCoverPath, filmCoverDto.getPicture());
            } catch (Exception e) {
                throw new RuntimeException(SAVE_STORAGE_COVER_ERROR);
            }

            deleteOldCower(selection.getPicture());

            selection.setPicture(newCover);
            selectionRepository.save(selection);
        } else {
            throw new RuntimeException(NOT_ENOUGH_RIGHT);
        }

    }

    @Override
    public ResponsePictureDto getSelectionCover(long selectionId) {
        var selection = selectionRepository.findById(selectionId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
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
    public void deleteSelectionCover(DeleteSelectionDto selectionDto) {
        var selection = selectionRepository.findById(selectionDto.getSelectionId())
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        var user = userRepository.findById(selectionDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        if (selection.getOwner() == user.getId()) {
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
}
