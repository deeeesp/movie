package ru.stazaev.store.entitys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "selection")
public class Selection extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "selection_sequence")
    private long id;
    private String tag;
    private String name;
    private long owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private Picture picture;


    @JsonIgnoreProperties("selection")
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "film_selection",
            joinColumns = {@JoinColumn(name = "selection_id")},
            inverseJoinColumns = {@JoinColumn(name = "film_id")})
    private List<Film> films = new ArrayList<>();
}
