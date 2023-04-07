package ru.stazaev.store.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "selections")
public class Selection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "selection_id")
    private Long id;
    private String tag;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "films_selection",
            joinColumns = { @JoinColumn(name = "selection_id") },
            inverseJoinColumns = { @JoinColumn(name = "film_id") })
    private List<Film> films = new ArrayList<>();
}
