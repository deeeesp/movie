package ru.stazaev.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.stazaev.store.entitys.PictureType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectionDtoWithCover {
    @Schema(example = "1")
    private long id;
    @Schema(example = "my-new")
    private String tag;
    @Schema(example = "Для вечеринки")
    private String name;
    @Schema(example = "1")
    private long owner;
    private List<FilmDtoWithCover> films = new ArrayList<>();
    @Schema(example = "1")
    @JsonProperty("picture_id")
    private long pictureId;
}
