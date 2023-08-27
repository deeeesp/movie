package ru.stazaev.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.stazaev.api.dto.response.FilmDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveSelectionDto {
    @Schema(name = "tag филмьа")
    private String tag;
    private String name;
    private long owner;
    private List<FilmDto> films = new ArrayList<>();

}
