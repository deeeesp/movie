package ru.stazaev.api.mappers;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.MainPageRequestDTO;
import ru.stazaev.store.entitys.Film;
import ru.stazaev.store.entitys.Selection;

import java.util.List;
import java.util.Set;

@Service
public class MainPageRequestDTOMapper {
    private final SelectionDTOMapper selectionDTOMapper;
    private final FilmDTOMapper filmDTOMapper;

    public MainPageRequestDTOMapper(SelectionDTOMapper selectionDTOMapper, FilmDTOMapper filmDTOMapper) {
        this.selectionDTOMapper = selectionDTOMapper;
        this.filmDTOMapper = filmDTOMapper;
    }

    public MainPageRequestDTO entitiesToDTO(List<Film> films, List<Selection> selections){
        return MainPageRequestDTO.builder()
                .filmsDTO(filmDTOMapper.EntityListToDTO(films))
                .selectionsDTO(selectionDTOMapper.entityListToDto(selections))
                .build();
    }
}
