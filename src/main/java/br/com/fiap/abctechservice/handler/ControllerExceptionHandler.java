package br.com.fiap.abctechService.handler;


import br.com.fiap.abctechservice.handler.exception.ErrorMessageResponse;
import br.com.fiap.abctechservice.handler.exception.NotFoundException;
import br.com.fiap.abctechservice.handler.exception.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(OrderException.MinOrderTaskException.class)
    public ResponseEntity<ErrorMessageResponse> errorMinTasksRequired(OrderException.MinOrderTaskException ex) {
        ErrorMessageResponse error = new ErrorMessageResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderException.MaxOrderTaskException.class)
    public ResponseEntity<ErrorMessageResponse> errorMaxTasksException(OrderException.MaxOrderTaskException ex) {
        ErrorMessageResponse error = new ErrorMessageResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> validationErrorHandler(MethodArgumentNotValidException ex) {
        StringBuilder description = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            description.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("; ");
        });

        ErrorMessageResponse error = new ErrorMessageResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                "Invalid request"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessageResponse> validationErrorHandler(MethodArgumentTypeMismatchException ex) {
        ErrorMessageResponse error = new ErrorMessageResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> errorNotFoundException(NotFoundException ex) {
        ErrorMessageResponse error = new ErrorMessageResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
