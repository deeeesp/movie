package ru.stazaev.api.services;

import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.FilmDtoWithCover;
import ru.stazaev.api.dto.response.FilmSearchDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;
import ru.stazaev.store.entitys.Film;

import java.util.List;

public interface FilmService {
    List<FilmDtoWithCover> getAllFilms();

    FilmDtoWithCover getFilmById(long id);
    FilmDtoWithCover getFilmByIdWithCover(long id);

    void deleteFilmById(long id, String username);

    void saveFilm(FilmDto filmDTO, String username);
    FilmSearchDto getFilmByTitleOrPlot(String title);

    List<FilmDtoWithCover> getByTitle(String title);

    List<FilmDtoWithCover> getByTitleRatio(String title);

    List<FilmDtoWithCover> getByPlotRatio(String title);

    void updateFilmCover(UpdateFilmCoverDto filmCoverDto, String username);

    ResponsePictureDto getFilmCover(long filmId);

//    void deleteFilmCover(DeleteFilmDto filmDto);

    Film getById(long filmId);
}
