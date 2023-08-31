package ru.stazaev.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationDto {
    @Schema(example = "polka@mail.ru")
    private String email;
    @Schema(example = "polka")
    private String username;
    @Schema(example = "39d@sl55y90uv")
    private String password;
}
