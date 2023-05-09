package ru.stazaev.api.services.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.mappers.FilmDTOMapper;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.store.repositories.FilmRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmDTOMapper mapper;

    public FilmServiceImpl(FilmRepository filmRepository, FilmDTOMapper mapper) {
        this.filmRepository = filmRepository;
        this.mapper = mapper;
    }

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
    public void deleteFilmById(long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public void saveFilm(FilmDto filmDTO) {
        var film = mapper.DTOToEntity(filmDTO);
        filmRepository.save(film);
    }

    @Override
    public List<FilmDto> getByTitle(String title){
        var film = filmRepository.findByTitle(title);
        return film.map(mapper::EntityListToDTO)
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти фильм с таким названием"));
    }

    @Override
    public List<FilmDto> getByTitleRatio(String title){
        var film = filmRepository.findByRationTitle(title);
        return film.map(mapper::EntityListToDTO)
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти фильм с таким содержанием"));
    }

    @Override
    public List<FilmDto> getByPlotRatio(String title){
        var film = filmRepository.findByRationPlot(title);
        return film.map(mapper::EntityListToDTO)
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти фильм с таким содержанием"));
    }

}
