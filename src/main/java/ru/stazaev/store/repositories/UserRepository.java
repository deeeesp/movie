package ru.stazaev.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stazaev.store.entitys.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(long id);
}
