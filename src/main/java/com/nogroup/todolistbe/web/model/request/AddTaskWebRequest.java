package com.nogroup.todolistbe.web.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddTaskWebRequest {
  @NotEmpty
  private String task;
  @NotEmpty
  private String detail;
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date schedule;
}
