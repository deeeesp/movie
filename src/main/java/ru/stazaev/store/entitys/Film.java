package ru.stazaev.store.entitys;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private long id;
    private String title;
    private int releaseYear;
    private String country;
    private String director;
    private Long budget;
    private Long fees;
    private String plot;
}
