package com.nogroup.todolistbe.service.impl;

import com.nogroup.todolistbe.repository.TaskRepositoryCustom;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import com.nogroup.todolistbe.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.nogroup.todolistbe.service.TaskService;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepositoryCustom taskRepositoryCustom;

  @Override
  public Mono<GetTaskListWebResponse> getTaskList(GetTaskListWebRequest request) {
    return taskRepositoryCustom.getTaskPaged(request)
        .collectList()
        .map(this::constructGetTaskListWebResponse);
  }

  private GetTaskListWebResponse constructGetTaskListWebResponse(List<Task> taskList) {
    return GetTaskListWebResponse.builder().tasks(taskList).build();
  }

}
