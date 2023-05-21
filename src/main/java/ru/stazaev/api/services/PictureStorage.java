package ru.stazaev.api.services;

import org.springframework.web.multipart.MultipartFile;
import ru.stazaev.store.entitys.Picture;

public interface PictureStorage {
    void savePicture(String name, MultipartFile pictureFile) throws Exception;

    byte[] getPicture(String name) throws Exception;

    void deletePicture(String name) throws Exception;

    String getFilmCoverPath(Picture picture);

    String getSelectionCoverPath(Picture picture);
}
