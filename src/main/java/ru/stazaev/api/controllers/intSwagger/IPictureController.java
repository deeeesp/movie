package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.stazaev.api.dto.response.ResponsePictureDto;

import java.util.List;
@Tag(name = "Film API", description = "Allows to find pictures")
public interface IPictureController {
   ResponseEntity<ResponsePictureDto> getPictureById(long id) throws Exception;

   ResponseEntity<List<Long>> getCurrentPictures();
}
