package com.nogroup.todolistbe.web;

import com.nogroup.todolistbe.entity.Response;
import com.nogroup.todolistbe.entity.Task;
import com.nogroup.todolistbe.helper.ResponseHelper;
import com.nogroup.todolistbe.service.TaskService;
import com.nogroup.todolistbe.web.model.request.AddTaskWebRequest;
import com.nogroup.todolistbe.web.model.request.EditTaskWebRequest;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;
  private final Scheduler commonScheduler;

  @GetMapping
  public Mono<Response<GetTaskListWebResponse>> getTaskList(@Valid GetTaskListWebRequest getTaskListWebRequest) {
    return taskService
        .getTaskListPaged(getTaskListWebRequest)
        .map(ResponseHelper::ok)
        .subscribeOn(commonScheduler);
  }

  @PostMapping
  public Mono<Response<Task>> addTask(@RequestBody @Valid AddTaskWebRequest addTaskWebRequest, @RequestParam @Valid @NotEmpty(message = "Performer can't be empty") String performer) {
    return taskService
        .addTask(addTaskWebRequest, performer)
        .map(ResponseHelper::ok)
        .subscribeOn(commonScheduler);
  }

  @DeleteMapping
  public Mono<Response<Task>> deleteTask(@Valid @NotEmpty String id, @RequestParam @Valid @NotEmpty(message = "Performer can't be empty") String performer) {
    return taskService
        .deleteTask(id, performer)
        .map(ResponseHelper::ok)
        .subscribeOn(commonScheduler);
  }

  @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
  public Mono<Response<Task>> editTask(@RequestParam @Valid @NotEmpty(message = "Task id can't be empty") String id, @RequestBody @Valid
      EditTaskWebRequest editTaskWebRequest, @RequestParam @Valid @NotEmpty(message = "Performer can't be empty") String performer) {
    return taskService
        .editTask(id, editTaskWebRequest, performer)
        .map(ResponseHelper::ok)
        .subscribeOn(commonScheduler);
  }

}
