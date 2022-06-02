package com.nogroup.todolistbe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "Task not found");
  public HttpStatus httpStatus;
  public String errorMessage;

}
