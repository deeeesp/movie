package ru.stazaev.api.services.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.api.mappers.FilmDTOMapper;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.store.repositories.FilmRepository;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmDTOMapper mapper;

    public FilmServiceImpl(FilmRepository filmRepository, FilmDTOMapper mapper) {
        this.filmRepository = filmRepository;
        this.mapper = mapper;
    }

    @Override
    public List<FilmDTO> getTopFilms() {
        return mapper.EntityListToDTO(filmRepository.findAll());
    }

    @Override
    public FilmDTO getFilmById(long id) {
        var filmById = filmRepository.findById(id);
        return filmById.map(mapper::entityToDTO).orElse(null);
    }

    @Override
    public void deleteFilmById(long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public void saveFilm(FilmDTO filmDTO) {
        var film = mapper.DTOToEntity(filmDTO);
        filmRepository.save(film);
    }

    @Override
    public List<FilmDTO> getByTitle(String title){
        var film = filmRepository.findByTitle(title);
        return film.map(mapper::EntityListToDTO).orElse(null);
    }

    @Override
    public List<FilmDTO> getByTitleRatio(String title){
        var film = filmRepository.findByRationTitle(title);
        return film.map(mapper::EntityListToDTO).orElse(null);
    }

    @Override
    public List<FilmDTO> getByPlotRatio(String title){
        var film = filmRepository.findByRationPlot(title);
        return film.map(mapper::EntityListToDTO).orElse(null);
    }

}
