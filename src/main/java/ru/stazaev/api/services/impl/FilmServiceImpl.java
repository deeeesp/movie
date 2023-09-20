package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.FilmDtoWithCover;
import ru.stazaev.api.dto.response.FilmSearchDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.api.services.KinopoiskRequestService;
import ru.stazaev.api.services.PictureStorage;
import ru.stazaev.api.services.UserService;
import ru.stazaev.store.entitys.*;
import ru.stazaev.store.repositories.FilmRepository;
import ru.stazaev.store.repositories.PictureRepository;
import ru.stazaev.store.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    private final ModelMapper mapper;
    private final UserService userService;
    private final PictureRepository pictureRepository;
    private final PictureStorage pictureStorage;
    private final KinopoiskRequestService kinopoiskRequestService;

    @Override
    public List<FilmDtoWithCover> getAllFilms() {
        List<FilmDtoWithCover> result = new ArrayList<>();
        var films = filmRepository.findAll();
        for (Film film : films) {
            var temp = mapper.map(film, FilmDtoWithCover.class);
            temp.setPictureId(film.getPicture().getId());
            result.add(temp);
        }
        return result;
    }

    @Override
    public FilmDtoWithCover getFilmById(long id) {
        var filmById = getById(id);
        var film = mapper.map(filmById, FilmDtoWithCover.class);
        film.setPictureId(filmById.getPicture().getId());
        return film;
    }


    public FilmDtoWithCover getFilmByIdWithCover(long id) {
        var film = getById(id);
        var dto = mapper.map(film, FilmDtoWithCover.class);
        dto.setPictureId(film.getPicture().getId());
        return dto;
    }

    @Override
    public List<FilmDtoWithCover> getFilmsSortedByRating() {
        return getAllFilms().stream()
                .sorted(Comparator.comparing(
                        FilmDtoWithCover::getRating
                ).reversed())
                .collect(Collectors.toList());
    }


    public void deleteFilmById(long id, String username) {
        if (userService.isAdministrator(username)) {
            filmRepository.deleteById(id);
        } else {
            throw new RuntimeException(NOT_ENOUGH_RIGHT);
        }
    }

    public Long saveFilm(FilmDto filmDTO, String username) {
        if (userService.isAdministrator(username)) {
            var film = mapper.map(filmDTO, Film.class);
            film.setStatus(Status.ACTIVE);
            var picture = pictureRepository.getById(0L);
            film.setPicture(picture);
            var filmName = film.getTitle();
            var rating = kinopoiskRequestService.getMovieRatings(filmName);
            film.setRating(rating);
            film = filmRepository.save(film);
            return film.getId();
        } else {
            throw new RuntimeException(NOT_ENOUGH_RIGHT);
        }
    }

    @Override
    public FilmSearchDto getFilmByTitleOrPlot(String title) {
        FilmSearchDto result = new FilmSearchDto();
        result.setTitleFilms(getByTitleRatio(title));
        result.setPlotFilms(getByPlotRatio(title));
        return result;
    }


    public List<FilmDtoWithCover> getByTitle(String title) {
        var films = filmRepository.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        List<FilmDtoWithCover> result = new ArrayList<>();
        for (Film film : films) {
            var temp = mapper.map(film, FilmDtoWithCover.class);
            temp.setPictureId(film.getPicture().getId());

            result.add(temp);
        }
        return result;
    }

    @Override
    public List<FilmDtoWithCover> getByTitleRatio(String title) {
        var films = filmRepository.findByRationTitle(title)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        List<FilmDtoWithCover> result = new ArrayList<>();
        for (Film film : films) {
            var temp = mapper.map(film, FilmDtoWithCover.class);
            temp.setPictureId(film.getPicture().getId());
            result.add(temp);
        }
        return result;
    }

    @Override
    public List<FilmDtoWithCover> getByPlotRatio(String title) {
        var films = filmRepository.findByRationPlot(title)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
        List<FilmDtoWithCover> result = new ArrayList<>();
        for (Film film : films) {
            var temp = mapper.map(film, FilmDtoWithCover.class);
            temp.setPictureId(film.getPicture().getId());


            result.add(temp);
        }
        return result;
    }

    @Override
    public void updateFilmCover(UpdateFilmCoverDto filmCoverDto, String username) {
//        if (!userService.isAdministrator(username)) {
//            throw new AccessDeniedException(NOT_ENOUGH_RIGHT);
//        }
//        var film = getById(filmCoverDto.getFilmId());
//
//
//        Picture picture = new Picture();
//        picture.setPictureType(filmCoverDto.getPictureType());
//        picture = pictureRepository.save(picture);
//
//        String picturePath = pictureStorage.getFilmCoverPath(picture);
//        try {
//            pictureStorage.savePicture(picturePath, new MockMultipartFile(picturePath, filmCoverDto.getPicture()));
//        } catch (Exception e) {
//            pictureRepository.delete(picture);
//            throw new RuntimeException(SAVE_STORAGE_COVER_ERROR);
//        }
//
//        deleteFilmCover(film.getPicture());
//        film.setPicture(picture);
//        filmRepository.save(film);
    }

//    @Override
    public void deleteFilmCover(long filmId, String username) {
        if (!userService.isAdministrator(username)) {
            throw new AccessDeniedException(NOT_ENOUGH_RIGHT);
        }
        var film = getById(filmId);
        deleteFilmCover(film.getPicture());
    }

    public ResponsePictureDto getFilmCover(long filmId) {
        var film = getById(filmId);
        Picture cover = film.getPicture();
        if (cover != null) {
            String coverPath = pictureStorage.getFilmCoverPath(cover);
            byte[] coverData = null;
            try {
                coverData = pictureStorage.getPicture(coverPath);
            } catch (Exception e) {
                System.out.println(GET_STORAGE_COVER_ERROR);
            }
            return ResponsePictureDto.builder()
                    .data(coverData)
                    .pictureType(cover.getPictureType())
                    .build();
        }
        return null;
    }

    @Override
    public Film getById(long filmId) {
        return filmRepository.findById(filmId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT));
    }


    private void deleteFilmCover(Picture cover) {
        if (cover != null) {
            String coverPath = pictureStorage.getFilmCoverPath(cover);
            try {
                pictureStorage.deletePicture(coverPath);
            } catch (Exception e) {
                throw new RuntimeException(DELETE_STORAGE_COVER_ERROR);
            }
            pictureRepository.delete(cover);
        }
    }
}
