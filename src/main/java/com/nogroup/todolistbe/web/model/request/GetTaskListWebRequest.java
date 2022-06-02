package com.nogroup.todolistbe.web.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GetTaskListWebRequest {
  @NotNull
  @Min(1)
  private Integer pageSize;

  @NotNull
  @Min(1)
  private Integer page;

  private String filter;
  private String sortBy;
  private String sortDir;
}
