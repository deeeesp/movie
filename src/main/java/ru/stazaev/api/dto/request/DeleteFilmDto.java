package ru.stazaev.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteFilmDto {
    @JsonProperty("film_id")
    private long filmId;
    @JsonProperty("user_id")
    private long userId;
}
