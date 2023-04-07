package ru.stazaev.store.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "film_id")
    private long id;
    private String title;
    private int releaseYear;
    private String country;
    private String director;
    private Long budget;
    private Long fees;
    private String plot;

    @ManyToMany(mappedBy = "films")
    private List<Selection> selections = new ArrayList<>();
}
