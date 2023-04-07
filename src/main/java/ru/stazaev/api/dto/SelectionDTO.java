package ru.stazaev.api.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
