package ru.stazaev.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stazaev.store.entitys.PictureType;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePictureDto {
    @Schema(example = "JPG")
    @JsonProperty("picture_type")
    private PictureType pictureType;
    @Schema(example = "/9j/4AAQSkZJRgABAQAAAQAB...j4cj4cj4/wD4LcuH/wAP/9k=")
    private byte[] data;
}
