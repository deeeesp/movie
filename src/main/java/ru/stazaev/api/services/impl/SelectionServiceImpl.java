package ru.stazaev.api.services.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.request.DeleteSelectionDto;
import ru.stazaev.api.dto.request.SaveSelectionDto;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.mappers.SelectionDTOMapper;
import ru.stazaev.store.repositories.FilmRepository;
import ru.stazaev.store.repositories.SelectionRepository;

import java.util.NoSuchElementException;

@Service
public class SelectionServiceImpl implements ru.stazaev.api.services.SelectionService {
    private final SelectionRepository selectionRepository;
    private final FilmRepository filmRepository;
    private final SelectionDTOMapper mapper;

    public SelectionServiceImpl(SelectionRepository selectionRepository, FilmRepository filmRepository, SelectionDTOMapper mapper) {
        this.selectionRepository = selectionRepository;
        this.filmRepository = filmRepository;
        this.mapper = mapper;
    }

    @Override
    public SelectionDto getSelection() {
        return null;
    }

    @Override
    public SelectionDto getById(long id) {
        var findById = selectionRepository.findById(id);
        return findById.map(mapper::entityToDto)
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти подборку с таким id"));
    }

    public void save(SaveSelectionDto selectionDTO){
        var selection = mapper.DTOToEntity(selectionDTO);
        selectionRepository.save(selection);
    }


    @Override
    public void addFilm(long id, long filmId) {
        var film = filmRepository.findById(filmId);
        var selection = selectionRepository.findById(id);
        if (selection.isPresent() && film.isPresent()) {
            selection.get().getFilms().add(film.get());
            selectionRepository.save(selection.get());
        }else {
            throw new RuntimeException("Не удалось найти фильм или плейлист с таким id");
        }
    }

    @Override
    public void deleteFilm(long id, long filmId) {
        var film = filmRepository.findById(filmId);
        var selection = selectionRepository.findById(id);
        if (selection.isPresent()&& film.isPresent()) {
            selection.get().getFilms().remove(film.get());
            selectionRepository.save(selection.get());
        }else {
            throw new RuntimeException("Не удалось найти фильм или плейлист с таким id");
        }
    }

    @Override
    public void deleteSelectionById(DeleteSelectionDto selectionDto) {
        var selection = selectionRepository.findById(selectionDto.getSelectionId());
        if (selection.isPresent()){
            if (selection.get().getCreatorId() == selectionDto.getUserId()){
                selectionRepository.deleteById(selectionDto.getSelectionId());
            }else {
                throw new RuntimeException("Недостаточно прав");
            }
        }else {
            throw new NoSuchElementException("Не удалось найти плейлист с таким id");
        }
    }

    @Override
    public void deleteSelectionByTag(String tag) {
        selectionRepository.deleteByTag(tag);
    }
}
