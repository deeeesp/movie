package ru.stazaev.api.mappers;

import org.springframework.stereotype.Component;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.store.entitys.Selection;

import java.util.ArrayList;
import java.util.List;

@Component
public class SelectionDTOMapper {
    private final FilmDTOMapper filmDTOMapper;

    public SelectionDTOMapper(FilmDTOMapper filmDTOMapper) {
        this.filmDTOMapper = filmDTOMapper;
    }
    public SelectionDto entityToDto(Selection selection){
        return SelectionDto.builder()
                .name(selection.getName())
                .tag(selection.getTag())
                .films(filmDTOMapper.EntityListToDTO(selection.getFilms()))
                .build();
    }
    public List<SelectionDto> entityListToDto(List<Selection> selections) {
        List<SelectionDto> selectionsDTO = new ArrayList<>();
        for (Selection selection : selections){
            selectionsDTO.add(entityToDto(selection));
        }
        return selectionsDTO;
    }
    public Selection DTOToEntity(SelectionDto selectionDTO){
        return Selection.builder()
                .name(selectionDTO.getName())
                .tag(selectionDTO.getTag())
                .films(filmDTOMapper.DTOListToEntity(selectionDTO.getFilms()))
                .build();
    }
}
