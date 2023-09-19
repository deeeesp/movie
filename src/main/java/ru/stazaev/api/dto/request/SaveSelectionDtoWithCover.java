package ru.stazaev.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stazaev.api.dto.response.FilmDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveSelectionDtoWithCover {
    @Schema(example = "my-new")
    private String tag;
    @Schema(example = "Для вечеринки")
    private String name;
    @Schema(example = "1")
    private long owner;
    private List<FilmDto> films = new ArrayList<>();
    @Schema(example = "/9j/4AAQSkZJRgABAQAAAQAB...j4cj4cj4/wD4LcuH/wAP/9k=")
    private byte[] picture;
}
