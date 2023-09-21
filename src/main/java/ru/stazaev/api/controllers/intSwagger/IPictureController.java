package ru.stazaev.api.controllers.intSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.stazaev.api.dto.response.ApiErrorResponse;
import ru.stazaev.api.dto.response.ResponsePictureDto;

import java.util.List;
@Tag(name = "Picture API", description = "Allows to find pictures")
public interface IPictureController {
   @Operation(summary = "Получить изображение по id",
           responses = {
                   @ApiResponse(
                           content = {
                                   @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                           },
                           responseCode = "404", description = "Картинка не найдена"
                   ),
                   @ApiResponse(
                           content = {
                                   @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                           },
                           responseCode = "406", description = "Облачное хранилище не работает"
                   )
           })
   @ApiResponse(responseCode = "200", description = "Картинка получена")
   ResponseEntity<ResponsePictureDto> getPictureById(long id) throws Exception;

   @Operation(summary = "Получить список актульных картинок",
           responses = {
                   @ApiResponse(
                           content = {
                                   @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                           },
                           responseCode = "404", description = "Картинка не найдена"
                   )
           })
   @ApiResponse(responseCode = "200", description = "Картинки получены")
   ResponseEntity<List<Long>> getCurrentPictures();
}
