package ru.stazaev.store.repositories;

import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.store.entitys.Film;
import ru.stazaev.store.entitys.Selection;

import java.util.List;
import java.util.Optional;

public interface SelectionRepository extends JpaRepository<Selection, Long> {
    Optional<Selection> findById(long id);

    @Modifying
    @Transactional
    @Query("update Selection s set s.films =:filmList where s.id = 1")
    void updateFilms(@Param("filmList") List<Film> filmList);
}
