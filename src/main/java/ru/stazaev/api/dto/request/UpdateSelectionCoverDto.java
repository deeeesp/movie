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
public class UpdateSelectionCoverDto {
    @JsonProperty("selection_id")
    private long selectionId;
    @JsonProperty("picture_type")
    private PictureType pictureType;
    private MultipartFile picture;
}
