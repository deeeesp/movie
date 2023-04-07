package ru.stazaev.api.mappers;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.store.entitys.Film;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmDTOMapper {
    public FilmDTO entityToDTO(Film film){
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

    public List<FilmDTO> EntityListToDTO(List<Film> films){
        List<FilmDTO> filmsDTO = new ArrayList<>();
        for (Film film : films) {
            filmsDTO.add(entityToDTO(film));
        }
        return filmsDTO;
    }

    public List<Film> DTOListToEntity(List<FilmDTO> filmsDTO) {
        List<Film> films = new ArrayList<>();
        for (FilmDTO filmDTO : filmsDTO){
            films.add(DTOToEntity(filmDTO));
        }
        return films;
    }

    private Film DTOToEntity(FilmDTO filmDTO) {
        return Film.builder()
                .title(filmDTO.getTitle())
                .budget(filmDTO.getBudget())
                .country(filmDTO.getCountry())
                .director(filmDTO.getDirector())
                .fees(filmDTO.getFees())
                .plot(filmDTO.getPlot())
                .releaseYear(filmDTO.getReleaseYear())
                .build();
    }
}
