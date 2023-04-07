package ru.stazaev.api.services;

import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.api.dto.SelectionDTO;

public interface SelectionService {

    SelectionDTO getSelection();

    SelectionDTO getById(long id);

    void save(SelectionDTO selectionDTO);

    void addFilm(long id, long filmId);

    void deleteFilm(long id, long filmId);

    void deleteSelectionById(long id);

    @Transactional
    void deleteSelectionByTag(String tag);

}
