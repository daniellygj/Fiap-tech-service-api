package br.com.fiap.abctechservice.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String object, Long id) {
        super(object + " with id " + id + " does not exists.");
    }

    public NotFoundException(String object, String email) {
        super(object + " with id " + email + " does not exists.");
    }
}
