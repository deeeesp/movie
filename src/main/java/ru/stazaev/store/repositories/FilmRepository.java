package ru.stazaev.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.stazaev.store.entitys.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Optional<Film> findById(Long id);
    @Query("select f from Film f")
    List<Film> findAll();
    void deleteById(long id);
    Optional<List<Film>> findByTitle(String title);

    @Query("select f from Film f where f.title ilike %:value%")
    Optional<List<Film>> findByRationTitle(@Param("value") String value);

    @Query("select f from Film f where f.plot ilike %:value%")
    Optional<List<Film>> findByRationPlot(@Param("value") String value);

    @Query("select f.picture.id from Film f")
    List<Long> findPictureId();
}
