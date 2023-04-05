package ru.stazaev.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilmDTO {
    private String title;
    private int releaseYear;
    private String country;
    private String director;
    private Long budget;
    private Long fees;
    private String plot;
}
