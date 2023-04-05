package ru.stazaev.api.services;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.api.mappers.MovieDTOMapper;
import ru.stazaev.store.entitys.Film;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmServicePlug implements FilmService {
    private final MovieDTOMapper movieDTOMapper;

    public FilmServicePlug(MovieDTOMapper movieDTOMapper) {
        this.movieDTOMapper = movieDTOMapper;
    }

    @Override
    public List<FilmDTO> getTopFilms() {
        List<FilmDTO> moviesDto = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            moviesDto.add(movieDTOMapper.EntityToMovieDTO(new Film()));
        }
        return moviesDto;
    }

    @Override
    public FilmDTO getFilmById(long id) {
        return movieDTOMapper.EntityToMovieDTO(new Film());
    }
}
