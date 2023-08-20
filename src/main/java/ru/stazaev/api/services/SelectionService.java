package ru.stazaev.api.services;

import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.api.dto.request.*;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.store.entitys.Selection;

public interface SelectionService {

    SelectionDto getSelection();

    SelectionDto getById(long id);

    SelectionDto getByTag(String tag);

    void save(SaveSelectionDto selectionDTO);

    void addFilm(long id, long filmId);
    void addFilmToFavorite(long id, long filmId);

    void deleteFilm(long id, long filmId);

    void deleteSelectionById(DeleteSelectionDto selectionDto);

    @Transactional
    void deleteSelectionByTag(String tag);

    void updateSelectionCover(UpdateSelectionCoverDto selectionCoverDto);

    ResponsePictureDto getSelectionCover(long selectionId);

    void deleteSelectionCover(DeleteSelectionDto selectionDto);

    Selection createFavoriteSelection(long id);

}
