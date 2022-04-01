package br.com.fiap.abctechservice.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageResponse {

    private Integer statusCode;

    private Date timestamp;

    private String message;

}
