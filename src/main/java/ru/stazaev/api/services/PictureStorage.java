package ru.stazaev.api.services;

import org.springframework.web.multipart.MultipartFile;
import ru.stazaev.api.dto.response.FilmDtoWithCover;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.store.entitys.Film;
import ru.stazaev.store.entitys.Picture;

import java.util.List;

public interface PictureStorage {
    void savePicture(String name, MultipartFile pictureFile) throws Exception;

    byte[] getPicture(String name) throws Exception;

    void deletePicture(String name) throws Exception;

    String getFilmCoverPath(Picture picture);

    String getSelectionCoverPath(Picture picture);
    ResponsePictureDto getPicture(long id) throws Exception;
    List<Long> getActualPictures();
}
