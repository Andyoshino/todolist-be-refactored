package com.nogroup.todolistbe.web.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GetHistoryWebRequest {
  @NotNull(message = "Page size can't be null")
  @Min(value = 1, message = "Page size value is invalid")
  private Integer pageSize;

  @NotNull(message = "Page number can't be null")
  @Min(value = 1, message = "Page number is invalid")
  private Integer page;
}
