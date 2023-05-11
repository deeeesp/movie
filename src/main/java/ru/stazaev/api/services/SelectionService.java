package ru.stazaev.api.services;

import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.api.dto.response.SelectionDto;

public interface SelectionService {

    SelectionDto getSelection();

    SelectionDto getById(long id);

    void save(SelectionDto selectionDTO);

    void addFilm(long id, long filmId);

    void deleteFilm(long id, long filmId);

    void deleteSelectionById(long id);

    @Transactional
    void deleteSelectionByTag(String tag);

}
