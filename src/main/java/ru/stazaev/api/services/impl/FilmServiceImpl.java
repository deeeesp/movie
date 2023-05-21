package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.request.DeleteFilmDto;
import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.mappers.FilmDTOMapper;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.api.services.PictureStorage;
import ru.stazaev.store.entitys.*;
import ru.stazaev.store.repositories.FilmRepository;
import ru.stazaev.store.repositories.PictureRepository;
import ru.stazaev.store.repositories.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final String NO_SUCH_ELEMENT = "Не удалось найти элемент с таким id";
    private final String NOT_ENOUGH_RIGHT = "Недостаточно прав";
    private final String SAVE_STORAGE_COVER_ERROR = "Ошибка при загрузке изображения в облако";
    private final String DELETE_STORAGE_COVER_ERROR = "Ошибка при удалении изображения из облака";
    private final String GET_STORAGE_COVER_ERROR = "Ошибка при получении изображения из облака";

    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final FilmDTOMapper mapper;
    private final PictureRepository pictureRepository;
    private final PictureStorage pictureStorage;

    @Override
    public List<FilmDto> getTopFilms() {
        return mapper.EntityListToDTO(filmRepository.findAll());
    }

    @Override
    public FilmDto getFilmById(long id) {
        var filmById = filmRepository.findById(id);
        return filmById.map(mapper::entityToDTO).orElse(null);
    }

    @Override
    public void deleteFilmById(DeleteFilmDto filmDto) {
        var user = userRepository.findById(filmDto.getUserId());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)) {
            filmRepository.deleteById(filmDto.getFilmId());
        } else {
            throw new RuntimeException(NOT_ENOUGH_RIGHT);
        }
    }

    @Override
    public void saveFilm(FilmDto filmDTO) {
        var film = mapper.DTOToEntity(filmDTO);
        film.setStatus(Status.ACTIVE);
        filmRepository.save(film);
    }

    @Override
    public List<FilmDto> getByTitle(String title) {
        var film = filmRepository.findByTitle(title);
        return film.map(mapper::EntityListToDTO)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
    }

    @Override
    public List<FilmDto> getByTitleRatio(String title) {
        var film = filmRepository.findByRationTitle(title);
        return film.map(mapper::EntityListToDTO)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
    }

    @Override
    public List<FilmDto> getByPlotRatio(String title) {
        var film = filmRepository.findByRationPlot(title);
        return film.map(mapper::EntityListToDTO)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
    }

    @Override
    public void updateFilmCover(UpdateFilmCoverDto filmCoverDto) {
        var film = checkUserRoleGetFilm(filmCoverDto.getUserId(), filmCoverDto.getFilmId());

        Picture picture = new Picture();
        picture.setPictureType(filmCoverDto.getPictureType());
        picture = pictureRepository.save(picture);

        String picturePath = pictureStorage.getFilmCoverPath(picture);
        try {
            pictureStorage.savePicture(picturePath, filmCoverDto.getPicture());
        } catch (Exception e) {
            pictureRepository.delete(picture);
            throw new RuntimeException(SAVE_STORAGE_COVER_ERROR);
        }

        deleteFilmCover(film.getPicture());

        film.setPicture(picture);
        filmRepository.save(film);
    }

    @Override
    public void deleteFilmCover(DeleteFilmDto filmDto) {
        var film = checkUserRoleGetFilm(filmDto.getUserId(), filmDto.getFilmId());
        deleteFilmCover(film.getPicture());
    }

    @Override
    public ResponsePictureDto getFilmCover(long filmId) {
        var film = filmRepository.findById(filmId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        Picture cover = film.getPicture();
        if (cover != null){
            String coverPath = pictureStorage.getFilmCoverPath(cover);
            byte[] coverData;
            try {
                coverData = pictureStorage.getPicture(coverPath);
            }catch (Exception e){
                throw new RuntimeException(GET_STORAGE_COVER_ERROR);
            }
            return ResponsePictureDto.builder()
                    .data(coverData)
                    .pictureType(cover.getPictureType())
                    .build();
        }
        return null;
    }


    private Film checkUserRoleGetFilm(long userId, long filmId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException(NOT_ENOUGH_RIGHT);
        }
        return filmRepository.findById(filmId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
    }

    private void deleteFilmCover(Picture cover){
        if (cover!= null){
            String coverPath = pictureStorage.getFilmCoverPath(cover);
            try {
                pictureStorage.deletePicture(coverPath);
            }catch (Exception e){
                throw new RuntimeException(DELETE_STORAGE_COVER_ERROR);
            }
            pictureRepository.delete(cover);
        }
    }
}
