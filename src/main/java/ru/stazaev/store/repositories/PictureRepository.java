package ru.stazaev.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stazaev.store.entitys.Picture;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Optional<Picture> findById(long id);

}
