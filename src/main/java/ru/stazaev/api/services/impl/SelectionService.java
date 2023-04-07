package ru.stazaev.api.services.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.SelectionDTO;
import ru.stazaev.api.mappers.SelectionDTOMapper;
import ru.stazaev.store.repositories.SelectionRepository;

@Service
public class SelectionService implements ru.stazaev.api.services.SelectionService {
    private final SelectionRepository selectionRepository;
    private final SelectionDTOMapper mapper;
    
    public SelectionService(SelectionRepository selectionRepository, SelectionDTOMapper mapper) {
        this.selectionRepository = selectionRepository;
        this.mapper = mapper;
    }

    @Override
    public SelectionDTO getSelection() {
        return null;
    }

    @Override
    public SelectionDTO getById(long id) {
        var findById = selectionRepository.findById(id);
        return findById.map(mapper::entityToDto).orElse(null);
    }

    public void save(SelectionDTO selectionDTO){
        var selection = mapper.DTOToEntity(selectionDTO);
        selectionRepository.save(selection);
    }
}
