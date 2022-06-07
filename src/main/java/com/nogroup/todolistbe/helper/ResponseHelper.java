package com.nogroup.todolistbe.helper;

import com.nogroup.todolistbe.entity.Response;
import org.springframework.http.HttpStatus;

public class ResponseHelper {

  public static <T> Response<T> ok(T data) {
    return status(HttpStatus.OK, data);
  }

  public static <T> Response<T> badRequest() {
    return badRequest(null);
  }

  public static <T> Response<T> badRequest(T data) {
    return status(HttpStatus.BAD_REQUEST, data);
  }

  public static <T> Response<T> notFound() {
    return notFound(null);
  }

  public static <T> Response<T> notFound(T data) {
    return status(HttpStatus.NOT_FOUND, data);
  }

  public static <T> Response<T> status(HttpStatus httpStatus, T data) {
    return Response.<T>builder().code(httpStatus.value()).status(httpStatus.name()).data(data).build();
  }

}
