package ru.stazaev.api.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectionDto {
    private String tag;
    private String name;
    private List<FilmDto> films = new ArrayList<>();
}
