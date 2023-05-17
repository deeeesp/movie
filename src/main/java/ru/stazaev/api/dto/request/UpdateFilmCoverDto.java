package ru.stazaev.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UpdateFilmCoverDto {
    @JsonProperty("film_id")
    private long filmId;
    @JsonProperty("user_id")
    private long userId;
    private PictureType pictureType;
    private MultipartFile picture;
}
