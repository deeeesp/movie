package ru.stazaev.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long filmId;
    private PictureType pictureType;
    private MultipartFile picture;
}
