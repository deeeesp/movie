package ru.stazaev.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Schema(example = "1")
    private long id;
    @Schema(example = "polka@mail.ru")
    private String email;
    @Schema(example = "polka")
    private String username;

}
