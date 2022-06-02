package com.nogroup.todolistbe.service.impl;

import com.nogroup.todolistbe.exception.CustomException;
import com.nogroup.todolistbe.exception.ErrorCode;
import com.nogroup.todolistbe.repository.TaskRepository;
import com.nogroup.todolistbe.repository.TaskRepositoryCustom;
import com.nogroup.todolistbe.web.model.request.AddTaskWebRequest;
import com.nogroup.todolistbe.web.model.request.EditTaskWebRequest;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import com.nogroup.todolistbe.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.nogroup.todolistbe.service.TaskService;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepositoryCustom taskRepositoryCustom;
  private final TaskRepository taskRepository;

  @Override
  public Mono<GetTaskListWebResponse> getTaskListPaged(GetTaskListWebRequest request) {
    return taskRepositoryCustom.getTaskPaged(request)
        .collectList()
        .map(this::constructGetTaskListWebResponse);
  }

  private GetTaskListWebResponse constructGetTaskListWebResponse(List<Task> taskList) {
    return GetTaskListWebResponse.builder().tasks(taskList).build();
  }

  @Override
  public Mono<Task> addTask(AddTaskWebRequest addTaskWebRequest) {
    Task task = new Task();
    BeanUtils.copyProperties(addTaskWebRequest, task);
    return taskRepository.save(task);
  }

  @Override
  public Mono<Task> deleteTask(String id) {
    return taskRepository
        .findById(id)
        .switchIfEmpty(Mono.defer(() -> Mono.error(new CustomException(ErrorCode.TASK_NOT_FOUND))))
        .flatMap(task -> taskRepository.deleteById(id).thenReturn(task));
  }

  @Override
  public Mono<Task> editTask(String id, EditTaskWebRequest editTaskWebRequest) {
    Task task = new Task();
    BeanUtils.copyProperties(editTaskWebRequest, task);
    task.setId(id);
    return taskRepository.save(task);
  }

}
