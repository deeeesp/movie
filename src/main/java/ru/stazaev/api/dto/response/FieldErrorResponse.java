package ru.stazaev.api.dto.response;

public record FieldErrorResponse(

        String field,

        String message

){

    public static FieldErrorResponse of(String field, String  message) {
        return new FieldErrorResponse(field, message);
    }

}
