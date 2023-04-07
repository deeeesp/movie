package ru.stazaev.api.services.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.FilmDTO;
import ru.stazaev.api.mappers.FilmDTOMapper;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.store.repositories.FilmRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
