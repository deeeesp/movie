package ru.stazaev.api.mappers;

import org.springframework.stereotype.Component;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.store.entitys.Film;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmDTOMapper {
    public FilmDto entityToDTO(Film film){
        return FilmDto.builder()
                .title(film.getTitle())
                .releaseYear(film.getReleaseYear())
                .country(film.getCountry())
                .director(film.getDirector())
                .budget(film.getBudget())
                .fees(film.getFees())
                .plot(film.getPlot())
                .build();
    }

    public List<FilmDto> EntityListToDTO(List<Film> films){
        List<FilmDto> filmsDTO = new ArrayList<>();
        for (Film film : films) {
            filmsDTO.add(entityToDTO(film));
        }
        return filmsDTO;
    }

    public List<Film> DTOListToEntity(List<FilmDto> filmsDTO) {
        List<Film> films = new ArrayList<>();
        for (FilmDto filmDTO : filmsDTO){
            films.add(DTOToEntity(filmDTO));
        }
        return films;
    }

    public Film DTOToEntity(FilmDto filmDTO) {
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
