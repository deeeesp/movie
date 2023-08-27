package ru.stazaev.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokensDto {
    private String accessToken;
    private String refreshToken;
    private long id;
}
