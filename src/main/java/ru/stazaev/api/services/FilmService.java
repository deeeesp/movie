package ru.stazaev.api.services;

import ru.stazaev.api.dto.FilmDTO;

import java.util.List;
import java.util.Set;

public interface FilmService {
    List<FilmDTO> getTopFilms();
    FilmDTO getFilmById(long id);
}
