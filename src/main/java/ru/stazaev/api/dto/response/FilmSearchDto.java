package ru.stazaev.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmSearchDto {
    @JsonProperty("title_films")
    private List<FilmDtoWithCover> titleFilms = new ArrayList<>();
    @JsonProperty("plot_films")
    private List<FilmDtoWithCover> plotFilms = new ArrayList<>();

}
