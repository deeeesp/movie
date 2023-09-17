package ru.stazaev.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stazaev.api.controllers.intSwagger.IPictureController;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.services.PictureStorage;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "https://movie-genie-131a7.web.app")
@RestController
@RequestMapping("/api/picture")
public class PictureController implements IPictureController {
    private final PictureStorage pictureStorage;
    private final String GET_COVER = "/get/{picture_id}";
    private final String GET_ACTUAL_IDS_COVER = "/get/actual-ids";

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @GetMapping(GET_COVER)
    public ResponseEntity<ResponsePictureDto> getPictureById(@PathVariable("picture_id") long id) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pictureStorage.getPicture(id));
    }

    @CrossOrigin(origins = "https://movie-genie-131a7.web.app")
    @GetMapping(GET_ACTUAL_IDS_COVER)
    public ResponseEntity<List<Long>> getCurrentPictures() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pictureStorage.getActualPictures());
    }


}
