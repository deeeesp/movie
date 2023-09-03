package ru.stazaev.api.exception;

import org.springframework.security.core.AuthenticationException;

public class AlreadyRegisteredUserException extends AuthenticationException {

    public AlreadyRegisteredUserException(String message) {
        super(message);
    }

}
