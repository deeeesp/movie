package ru.stazaev.api.mappers;

import org.springframework.stereotype.Component;
import ru.stazaev.api.dto.request.MainPageRequestDto;
import ru.stazaev.store.entitys.Film;
import ru.stazaev.store.entitys.Selection;

import java.util.List;

@Component
public class MainPageRequestDTOMapper {
    private final SelectionDTOMapper selectionDTOMapper;
    private final FilmDTOMapper filmDTOMapper;

    public MainPageRequestDTOMapper(SelectionDTOMapper selectionDTOMapper, FilmDTOMapper filmDTOMapper) {
        this.selectionDTOMapper = selectionDTOMapper;
        this.filmDTOMapper = filmDTOMapper;
    }

    public MainPageRequestDto entitiesToDTO(List<Film> films, List<Selection> selections){
        return MainPageRequestDto.builder()
                .filmsDTO(filmDTOMapper.EntityListToDTO(films))
                .selectionsDTO(selectionDTOMapper.entityListToDto(selections))
                .build();
    }
}
