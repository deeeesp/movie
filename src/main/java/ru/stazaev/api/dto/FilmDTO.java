package ru.stazaev.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilmDTO {
    private String title;
    @JsonProperty("release_year")
    private int releaseYear;
    private String country;
    private String director;
    private Long budget;
    private Long fees;
    private String plot;
}
