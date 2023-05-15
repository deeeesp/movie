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
public class DeleteSelectionDto {
    @JsonProperty("selection_id")
    private long selectionId;
    @JsonProperty("user_id")
    private long userId;
}
