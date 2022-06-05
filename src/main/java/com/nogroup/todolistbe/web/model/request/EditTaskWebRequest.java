package com.nogroup.todolistbe.web.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EditTaskWebRequest {
  @NotEmpty(message = "Task can't be empty")
  private String task;
  @NotEmpty(message = "Task's detail can't be empty")
  private String detail;
  @NotNull(message = "Task's schedule can't be empty")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date schedule;
}
