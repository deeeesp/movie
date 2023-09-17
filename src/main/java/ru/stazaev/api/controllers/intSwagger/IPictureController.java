package ru.stazaev.api.controllers.intSwagger;

import org.springframework.http.ResponseEntity;
import ru.stazaev.api.dto.response.ResponsePictureDto;

import java.util.List;

public interface IPictureController {
   ResponseEntity<ResponsePictureDto> getPictureById(long id) throws Exception;

   ResponseEntity<List<Long>> getCurrentPictures();
}
