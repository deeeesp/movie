package ru.stazaev.store.entitys;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {

    @Enumerated(EnumType.STRING)
    private Status status;
}
