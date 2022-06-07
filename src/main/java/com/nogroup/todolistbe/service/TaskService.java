package com.nogroup.todolistbe.service;

import com.nogroup.todolistbe.entity.Task;
import com.nogroup.todolistbe.web.model.request.AddTaskWebRequest;
import com.nogroup.todolistbe.web.model.request.EditTaskWebRequest;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import reactor.core.publisher.Mono;

public interface TaskService {
  Mono<GetTaskListWebResponse> getTaskListPaged(GetTaskListWebRequest request);

  Mono<Task> addTask(AddTaskWebRequest addTaskWebRequest, String performer);

  Mono<Task> deleteTask(String id, String performer);

  Mono<Task> editTask(String id, EditTaskWebRequest editTaskWebRequest, String performer);
}
