package com.nogroup.todolistbe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {
  private Integer code;
  private String status;
  private List<String> errors;
  private T data;
}
