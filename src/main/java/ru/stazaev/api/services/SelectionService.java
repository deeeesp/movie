package ru.stazaev.api.services;

import ru.stazaev.api.dto.SelectionDTO;

public interface SelectionService {

    SelectionDTO getSelection();

    SelectionDTO getById(long id);

    void save(SelectionDTO selectionDTO);
}
