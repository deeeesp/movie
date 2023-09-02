package ru.stazaev.api.services;

import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.api.dto.request.*;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.store.entitys.Selection;

public interface SelectionService {

    SelectionDto getSelection();

    SelectionDto getById(long selectionId);

    Selection getSelectionById(long selectionId);

    SelectionDto getByTag(String tag);

    void saveNewSelection(SaveSelectionDto selectionDTO);

    void addFilmToCustomSelection(long selectionId, long filmId, String username);
    void addFilmToFavorite(String username, long filmId);

    void deleteFilmFromSelection(long selectionId, long filmId, String username);

    void deleteSelectionById(long selectionId, String username);

    @Transactional
    void deleteSelectionByTag(String tag, String username);

    void updateSelectionCover(UpdateSelectionCoverDto selectionCoverDto, String username);

    ResponsePictureDto getSelectionCover(long selectionId);

    void deleteSelectionCover(Long selectionId, String username);

    Selection createFavoriteSelection(long id);

}
