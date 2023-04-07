package ru.stazaev.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MainPageRequestDTO {
    @JsonProperty("films")
    List<FilmDTO> filmsDTO;
    @JsonProperty("selections")
    List<SelectionDTO> selectionsDTO;

}
