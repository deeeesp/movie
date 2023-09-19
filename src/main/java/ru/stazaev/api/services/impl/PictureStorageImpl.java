package ru.stazaev.api.services.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.services.PictureStorage;
import ru.stazaev.store.entitys.Picture;
import ru.stazaev.store.repositories.FilmRepository;
import ru.stazaev.store.repositories.PictureRepository;
import ru.stazaev.store.repositories.SelectionRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PictureStorageImpl implements PictureStorage {
    @Value("${yandex.cloud.storage.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;
    private final PictureRepository pictureRepository;
    private final FilmRepository filmRepository;
    private final SelectionRepository selectionRepository;

    @Override
    public void savePicture(String name, MultipartFile pictureFile) throws IOException, AmazonClientException {
        s3Client.putObject(bucketName, name, pictureFile.getInputStream(), new ObjectMetadata());
    }

    @Override
    public byte[] getPicture(String name) throws IOException, AmazonClientException {
        S3Object s3Object = s3Client.getObject(bucketName, name);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        return IOUtils.toByteArray(inputStream);
    }

    @Override
    public void deletePicture(String name) throws AmazonClientException {
        s3Client.deleteObject(bucketName, name);
    }

    @Override
    public String getFilmCoverPath(Picture picture) {
        return  picture.getId() + "." + String.valueOf(picture.getPictureType()).toLowerCase();
    }

    @Override
    public String getSelectionCoverPath(Picture picture) {
        return  picture.getId() + "." + String.valueOf(picture.getPictureType()).toLowerCase();
    }

    @Override
    public ResponsePictureDto getPicture(long id) {
        var picture = pictureRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Картинка не найдена"));
        var path = picture.getId() + "." + String.valueOf(picture.getPictureType()).toLowerCase();
        try {
            var pic = getPicture(path);
            return ResponsePictureDto.builder()
                    .data(pic)
                    .pictureType(picture.getPictureType())
                    .build();
        }catch (Exception e){
            System.out.println(e);
        }
        return ResponsePictureDto.builder()
                .data(null)
                .build();
    }

    @Override
    public List<Long> getActualPictures() {
        var result = filmRepository.findPictureId();
        result.addAll(selectionRepository.findPictureId());
        return result;
    }
}
