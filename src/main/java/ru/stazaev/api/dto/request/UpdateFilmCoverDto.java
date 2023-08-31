package ru.stazaev.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import ru.stazaev.store.entitys.PictureType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFilmCoverDto {
    @JsonProperty("film_id")
    @Schema(example = "10")
    private long filmId;
    @JsonProperty("picture_type")
    @Schema(example = "JPG")
    private PictureType pictureType;
    @Schema(example = "/9j/4AAQSkZJRgABAQAAAQAB...j4cj4cj4/wD4LcuH/wAP/9k=")
    private byte[] picture;
}
