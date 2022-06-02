package com.nogroup.todolistbe.entity.helper;

import com.nogroup.todolistbe.entity.Response;
import org.springframework.http.HttpStatus;

public class ResponseHelper {

  public static <T> Response ok(T data) {
    return status(HttpStatus.OK, data);
  }

  public static <T> Response badRequest() {
    return badRequest(null);
  }

  public static <T> Response badRequest(T data) {
    return status(HttpStatus.BAD_REQUEST, data);
  }

  public static <T> Response notFound() {
    return notFound(null);
  }

  public static <T> Response notFound(T data) {
    return status(HttpStatus.NOT_FOUND, data);
  }

  public static <T> Response status(HttpStatus httpStatus, T data) {
    return Response.builder().code(httpStatus.value()).status(httpStatus.name()).data(data).build();
  }

}
