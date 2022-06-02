package com.nogroup.todolistbe.web;

import com.nogroup.todolistbe.entity.Response;
import com.nogroup.todolistbe.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({CustomException.class})
  public ResponseEntity<Response<Object>> validationExceptionHandler(CustomException err) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(Response.builder().code(err.getCode()).status(err.getStatus()).errors(err.getErrors()).build());
  }

}
