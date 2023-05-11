package ru.stazaev.api.services;

import ru.stazaev.api.dto.response.FilmDto;

import java.util.List;

public interface FilmService {
    List<FilmDto> getTopFilms();
    FilmDto getFilmById(long id);
    void deleteFilmById(long id);
    void saveFilm(FilmDto filmDTO);
    List<FilmDto> getByTitle(String title);
    List<FilmDto> getByTitleRatio(String title);
    List<FilmDto> getByPlotRatio(String title);
}
