package ru.stazaev.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.stazaev.store.entitys.PictureType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSelectionCoverDto {
    @JsonProperty("selection_id")
    @Schema(example = "1")
    private long selectionId;
    @JsonProperty("picture_type")
    @Schema(example = "JPG")
    private PictureType pictureType;
    @Schema(example = "/9j/4AAQSkZJRgABAQAAAQAB...j4cj4cj4/wD4LcuH/wAP/9k=")
    private byte[] picture;
}
