package ru.stazaev.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {
    private String title;
    @JsonProperty("release_year")
    private int releaseYear;
    private String country;
    private String director;
    private Long budget;
    private Long fees;
    private String plot;
}
