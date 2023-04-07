package ru.stazaev.api.services.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.SelectionDTO;
import ru.stazaev.api.mappers.SelectionDTOMapper;
import ru.stazaev.store.repositories.FilmRepository;
import ru.stazaev.store.repositories.SelectionRepository;

@Service
public class SelectionService implements ru.stazaev.api.services.SelectionService {
    private final SelectionRepository selectionRepository;
    private final FilmRepository filmRepository;
    private final SelectionDTOMapper mapper;

    public SelectionService(SelectionRepository selectionRepository, FilmRepository filmRepository, SelectionDTOMapper mapper) {
        this.selectionRepository = selectionRepository;
        this.filmRepository = filmRepository;
        this.mapper = mapper;
    }

    @Override
    public SelectionDTO getSelection() {
        return null;
    }

    @Override
    public SelectionDTO getById(long id) {
        var findById = selectionRepository.findById(id);
        return findById.map(mapper::entityToDto).orElse(null);
    }

    public void save(SelectionDTO selectionDTO){
        var selection = mapper.DTOToEntity(selectionDTO);
        selectionRepository.save(selection);
    }


    //TODO нормальный обработчик случаев, когда невалидны id
    @Override
    public void addFilm(long id, long filmId) {
        var film = filmRepository.findById(filmId);
        var selection = selectionRepository.findById(id);
        if (selection.isPresent()) {
            selection.get().getFilms().add(film.get());
            selectionRepository.save(selection.get());
        }
    }

    //TODO нормальный обработчик случаев, когда невалидны id
    @Override
    public void deleteFilm(long id, long filmId) {
        var film = filmRepository.findById(filmId);
        var selection = selectionRepository.findById(id);
        if (selection.isPresent()) {
            selection.get().getFilms().remove(film.get());
            selectionRepository.save(selection.get());
        }
    }
}
