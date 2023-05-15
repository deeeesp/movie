package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.request.DeleteFilmDto;
import ru.stazaev.api.dto.request.UserRegistrationDto;
import ru.stazaev.api.dto.response.FilmDto;
import ru.stazaev.api.mappers.FilmDTOMapper;
import ru.stazaev.api.services.FilmService;
import ru.stazaev.store.entitys.Role;
import ru.stazaev.store.repositories.FilmRepository;
import ru.stazaev.store.repositories.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final FilmDTOMapper mapper;

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
    public void deleteFilmById(DeleteFilmDto filmDto) {
        var user = userRepository.findById(filmDto.getUserId());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)){
            filmRepository.deleteById(filmDto.getFilmId());
        }else {
            throw new RuntimeException("Недостаточно ролей для выполнения этой операции");
        }
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
