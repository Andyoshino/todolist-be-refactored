package com.nogroup.todolistbe.web;

import com.nogroup.todolistbe.entity.Response;
import com.nogroup.todolistbe.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

  @ExceptionHandler({CustomException.class})
  public ResponseEntity<Response<Object>> validationExceptionHandler(CustomException err) {
    return ResponseEntity
        .status(err.getErrorCode().getHttpStatus())
        .body(Response.builder().code(err.getCode()).status(err.getStatus()).error(err.getError()).build());
  }

}
