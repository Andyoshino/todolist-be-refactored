package com.nogroup.todolistbe.repository;

import com.nogroup.todolistbe.entity.Task;
import reactor.core.publisher.Flux;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;

public interface TaskRepositoryCustom {
  Flux<Task> getTaskPaged(GetTaskListWebRequest request);
}
