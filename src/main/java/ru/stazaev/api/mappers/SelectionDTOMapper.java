package ru.stazaev.api.mappers;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.SelectionDTO;
import ru.stazaev.store.entitys.Selection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SelectionDTOMapper {
    private final FilmDTOMapper filmDTOMapper;

    public SelectionDTOMapper(FilmDTOMapper filmDTOMapper) {
        this.filmDTOMapper = filmDTOMapper;
    }
    public SelectionDTO entityToDto(Selection selection){
        return SelectionDTO.builder()
                .name(selection.getName())
                .tag(selection.getTag())
                .films(filmDTOMapper.EntityListToDTO(selection.getFilms()))
                .build();
    }
    public List<SelectionDTO> entityListToDto(List<Selection> selections) {
        List<SelectionDTO> selectionsDTO = new ArrayList<>();
        for (Selection selection : selections){
            selectionsDTO.add(entityToDto(selection));
        }
        return selectionsDTO;
    }
    public Selection DTOToEntity(SelectionDTO selectionDTO){
        return Selection.builder()
                .name(selectionDTO.getName())
                .tag(selectionDTO.getTag())
                .films(filmDTOMapper.DTOListToEntity(selectionDTO.getFilms()))
                .build();
    }
}
