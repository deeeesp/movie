package ru.stazaev.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokensDto {
    @Schema(example = "/AA432QSjytkZJRgABAQAAAQAB...j4cjwAP/9k=")
    @JsonProperty("access_token")
    private String accessToken;
    @Schema(example = "gernhuv9323y8sar...j4c023uhz")
    @JsonProperty("refresh_token")
    private String refreshToken;
    @Schema(example = "1")
    private long id;
}
