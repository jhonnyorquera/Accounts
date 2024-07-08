package com.banco.cuenta.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(value = CustomRuntimeException.class)
  public ResponseEntity<ErrorDto> customRuntimeExceptionHandler(CustomRuntimeException ex) {
    ErrorDto errorDto = ErrorDto.builder()
        .code("P-" + ex.getStatus().value())
        .message(ex.getMessage() != null ? ex.getMessage() : "error en aplicacion")
        .build();
    return new ResponseEntity<>(errorDto, ex.getStatus());
  }


  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<ErrorDto> runtimeExceptionHandler(RuntimeException ex) {
    System.out.println("message: "+ex);
    ErrorDto errorDto = ErrorDto.builder().code("P-500").message(ex.getMessage()!=null ? ex.getMessage(): "error en aplicacion").build();
    return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
  }


}
