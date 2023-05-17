package ru.stazaev.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stazaev.store.entitys.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
