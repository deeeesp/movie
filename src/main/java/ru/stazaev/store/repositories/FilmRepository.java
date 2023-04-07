package ru.stazaev.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stazaev.store.entitys.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Optional<Film> findById(Long id);
    List<Film> findAll();
    void deleteById(long id);
}
