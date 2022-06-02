package com.nogroup.todolistbe.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomException extends RuntimeException {
  private Integer code;
  private String status;
  private List<String> errors;
}
