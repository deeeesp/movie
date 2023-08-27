package ru.stazaev.api.services;

import ru.stazaev.api.dto.request.DeleteFilmDto;
import ru.stazaev.api.dto.request.UpdateFilmCoverDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.dto.response.FilmSearchDto;
import ru.stazaev.api.dto.response.ResponsePictureDto;

import java.util.List;

public interface FilmService {
    List<FilmDto> getTopFilms();

    FilmDto getFilmById(long id);

    void deleteFilmById(DeleteFilmDto filmDto);

    void saveFilm(FilmDto filmDTO);
    FilmSearchDto getFilm(String title);

    List<FilmDto> getByTitle(String title);

    List<FilmDto> getByTitleRatio(String title);

    List<FilmDto> getByPlotRatio(String title);

    void updateFilmCover(UpdateFilmCoverDto filmCoverDto);

    ResponsePictureDto getFilmCover(long filmId);

    void deleteFilmCover(DeleteFilmDto filmDto);
}
