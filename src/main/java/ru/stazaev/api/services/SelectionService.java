package ru.stazaev.api.services;

import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.api.dto.request.*;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.dto.response.SelectionDtoWithCover;
import ru.stazaev.store.entitys.Selection;

import java.util.List;

public interface SelectionService {

    SelectionDto getSelection();

    SelectionDtoWithCover getById(long selectionId);
    SelectionDtoWithCover getByIdWithCover(long selectionId);
    List<SelectionDtoWithCover> findAll();

    Selection getSelectionById(long selectionId);

    SelectionDtoWithCover getByTag(String tag);

    void saveNewSelection(SaveSelectionDto selectionDTO);
    void addFilmToCustomSelection(long selectionId, long filmId, String username);
    void addFilmToWillWatch(String username, long filmId);
    void deleteFilmFromSelection(long selectionId, long filmId, String username);
    void deleteFilmFromWillWatchSelection(long filmId, String username);
    void deleteSelectionById(long selectionId, String username);
    @Transactional
    void deleteSelectionByTag(String tag, String username);
    void updateSelectionCover(UpdateSelectionCoverDto selectionCoverDto, String username);
    ResponsePictureDto getSelectionCover(long selectionId);
    void deleteSelectionCover(Long selectionId, String username);
}
