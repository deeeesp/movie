package ru.stazaev.api.dto.response;

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
    private PictureType pictureType;
    private byte[] data;
}
