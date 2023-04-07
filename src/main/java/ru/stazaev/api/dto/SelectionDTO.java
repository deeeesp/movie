package ru.stazaev.api.dto;

import lombok.*;
import ru.stazaev.store.entitys.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectionDTO {
    private String tag;
    private String name;
    private List<FilmDTO> films = new ArrayList<>();
}
