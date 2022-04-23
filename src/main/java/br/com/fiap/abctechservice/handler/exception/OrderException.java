package br.com.fiap.abctechservice.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OrderException extends Throwable {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class MinOrderTaskException extends RuntimeException {
        public MinOrderTaskException() {
            super("An order must have at least one tasks");
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class MaxOrderTaskException extends RuntimeException {
        public MaxOrderTaskException() {
            super("An order must have less than 15 tasks");
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class OrderStartDateNullException extends RuntimeException {
        public OrderStartDateNullException() {
            super("The start date cannot be null");
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class OrderAlreadyClosedException extends RuntimeException {
        public OrderAlreadyClosedException(Long id) {
            super("The order with id " + id + " is already closed");
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class OrderNotStartedException extends RuntimeException {
        public OrderNotStartedException(Long id) {
            super("The order with id " + id + " was not started.");
        }
    }
}
