package ru.stazaev.api.services;

import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.api.dto.request.DeleteSelectionDto;
import ru.stazaev.api.dto.request.SaveSelectionDto;
import ru.stazaev.api.dto.response.SelectionDto;

public interface SelectionService {

    SelectionDto getSelection();

    SelectionDto getById(long id);

    void save(SaveSelectionDto selectionDTO);

    void addFilm(long id, long filmId);

    void deleteFilm(long id, long filmId);

    void deleteSelectionById(DeleteSelectionDto selectionDto);

    @Transactional
    void deleteSelectionByTag(String tag);

}
