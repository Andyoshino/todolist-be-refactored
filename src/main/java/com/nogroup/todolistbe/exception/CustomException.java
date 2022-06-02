package com.nogroup.todolistbe.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
  private Integer code;
  private String status;
  private String error;
  private ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.code = errorCode.getHttpStatus().value();
    this.status = errorCode.getHttpStatus().name();
    this.error = errorCode.getErrorMessage();
  }
}
