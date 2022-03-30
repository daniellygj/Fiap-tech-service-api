package br.com.fiap.abctechservice.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OrderException extends Throwable {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class MinOrderAssistsException extends RuntimeException {
        public MinOrderAssistsException() {
            super("An order must have at least one assist");
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class MaxOrderAssistsException extends RuntimeException {
        public MaxOrderAssistsException() {
            super("An order must have less than 15 assists");
        }
    }
}
