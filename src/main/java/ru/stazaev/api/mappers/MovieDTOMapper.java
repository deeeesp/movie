package ru.stazaev.api.mappers;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.store.entitys.Film;

@Service
public class MovieDTOMapper {
    public FilmDTO EntityToMovieDTO(Film film){
        return FilmDTO.builder()
                .title(film.getTitle())
                .releaseYear(film.getReleaseYear())
                .country(film.getCountry())
                .director(film.getDirector())
                .budget(film.getBudget())
                .fees(film.getFees())
                .plot(film.getPlot())
                .build();
    }
}
