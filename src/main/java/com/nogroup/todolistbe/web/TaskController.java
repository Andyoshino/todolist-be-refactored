package com.nogroup.todolistbe.web;

import com.nogroup.todolistbe.entity.helper.ResponseHelper;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import com.nogroup.todolistbe.entity.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import com.nogroup.todolistbe.service.TaskService;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
public class TaskController {

  private TaskService taskService;

  @GetMapping
  public Mono<Response<GetTaskListWebResponse>> getTaskList(@Valid GetTaskListWebRequest getTaskListWebRequest) {
    return taskService
        .getTaskList(getTaskListWebRequest)
        .map(ResponseHelper::ok);
  }

}
