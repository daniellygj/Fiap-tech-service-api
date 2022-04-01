//package br.com.fiap.abctechservice.handler;
//
//
//import br.com.fiap.abctechservice.handler.exception.ErrorMessageResponse;
//import br.com.fiap.abctechservice.handler.exception.OrderException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.Date;
//
//@ControllerAdvice
//public class ControllerExceptionHandler {
//
//
//    @ExceptionHandler(OrderException.MinOrderAssistsException.class)
//    public ResponseEntity<ErrorMessageResponse> errorMinAssistRequired(MinimumAssistsRequiredException ex){
//        ErrorMessageResponse error = new ErrorMessageResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                new Date(),
//                ex.getMessage(),
//                ex.getDescription()
//        );
//        return new ResponseEntity<ErrorMessageResponse>(error, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MaxAssistsException.class)
//    public ResponseEntity<ErrorMessageResponse> errorMaxAssisException(MaxAssistsException ex){
//        ErrorMessageResponse error = new ErrorMessageResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                new Date(),
//                ex.getMessage(),
//                ex.getDescription()
//        );
//        return new ResponseEntity<ErrorMessageResponse>(error, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorMessageResponse> validationErrorHandler(MethodArgumentNotValidException ex){
//        StringBuilder description = new StringBuilder();
//        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
//            description.append(fieldError.getField() + " - " + fieldError.getDefaultMessage() + "; ");
//        });
//        ErrorMessageResponse error = new ErrorMessageResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                new Date(),
//                "Invalid request",
//                description.toString()
//        );
//        return new ResponseEntity<ErrorMessageResponse>(error, HttpStatus.BAD_REQUEST);
//    }
//
//
//}
