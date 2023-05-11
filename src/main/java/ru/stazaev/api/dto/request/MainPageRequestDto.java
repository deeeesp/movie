package ru.stazaev.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.stazaev.api.dto.response.SelectionDto;
import ru.stazaev.api.dto.response.FilmDto;

import java.util.List;

@Getter
@Setter
@Builder
public class MainPageRequestDto {
    @JsonProperty("films")
    List<FilmDto> filmsDTO;
    @JsonProperty("selections")
    List<SelectionDto> selectionsDTO;

}
