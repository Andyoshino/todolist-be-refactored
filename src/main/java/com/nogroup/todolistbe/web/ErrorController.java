package com.nogroup.todolistbe.web;

import com.nogroup.todolistbe.entity.Response;
import com.nogroup.todolistbe.exception.CustomException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorController {

  @ExceptionHandler({CustomException.class})
  public ResponseEntity<Response<Object>> customExceptionHandler(CustomException err) {
    return ResponseEntity
        .status(err.getErrorCode().getHttpStatus())
        .body(Response.builder()
            .code(err.getCode())
            .status(err.getStatus())
            .errors(Arrays.asList(err.getError()))
            .build());
  }

  @ExceptionHandler({WebExchangeBindException.class})
  public ResponseEntity<Response<Object>> validationExceptionHandler(WebExchangeBindException err) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(Response.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .status(HttpStatus.BAD_REQUEST.name())
            .errors(new ArrayList<>(err
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet())))
            .build());
  }

}
