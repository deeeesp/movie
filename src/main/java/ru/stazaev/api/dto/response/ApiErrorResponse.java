package ru.stazaev.api.dto.response;

import java.util.List;

public record ApiErrorResponse(

        List<FieldErrorResponse> errors,

        String code,

        String exceptionName,

        String exceptionMessage

) {
}
