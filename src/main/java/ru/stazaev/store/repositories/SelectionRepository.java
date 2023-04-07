package ru.stazaev.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stazaev.store.entitys.Selection;

import java.util.Optional;

public interface SelectionRepository extends JpaRepository<Selection, Long> {
    Optional<Selection> findById(long id);

}
