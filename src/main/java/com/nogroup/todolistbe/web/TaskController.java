package com.nogroup.todolistbe.web;

import com.nogroup.todolistbe.entity.Response;
import com.nogroup.todolistbe.entity.Task;
import com.nogroup.todolistbe.entity.helper.ResponseHelper;
import com.nogroup.todolistbe.service.TaskService;
import com.nogroup.todolistbe.web.model.request.AddTaskWebRequest;
import com.nogroup.todolistbe.web.model.request.EditTaskWebRequest;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
public class TaskController {

  private TaskService taskService;

  @GetMapping
  public Mono<Response<GetTaskListWebResponse>> getTaskList(@Valid GetTaskListWebRequest getTaskListWebRequest) {
    return taskService
        .getTaskListPaged(getTaskListWebRequest)
        .map(ResponseHelper::ok);
  }

  @PostMapping
  public Mono<Response<Task>> addTask(@RequestBody @Valid AddTaskWebRequest addTaskWebRequest) {
    return taskService
        .addTask(addTaskWebRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping
  public Mono<Response<Task>> deleteTask(@Valid @NotEmpty String id) {
    return taskService
        .deleteTask(id)
        .map(ResponseHelper::ok);
  }

  @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
  public Mono<Response<Task>> editTask(@RequestParam @Valid @NotEmpty String id, @RequestBody @Valid
      EditTaskWebRequest editTaskWebRequest) {
    return taskService
        .editTask(id, editTaskWebRequest)
        .map(ResponseHelper::ok);
  }

}
