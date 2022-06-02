package com.nogroup.todolistbe.web.model.response;

import com.nogroup.todolistbe.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTaskListWebResponse {
  private List<Task> tasks;
}

