package ru.stazaev.store.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "picture")
@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "picture_sequence")
    private long id;

    @Enumerated(EnumType.STRING)
    private PictureType pictureType;
}
