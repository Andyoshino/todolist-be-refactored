package web;

import entity.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import web.model.request.GetTaskListWebRequest;
import web.model.response.GetTaskListWebResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/task")
@Validated
public class TaskController {

  @GetMapping
  public Mono<Response<GetTaskListWebResponse>> getTaskList(@Valid GetTaskListWebRequest getTaskListWebRequest) {
    return null;
  }

}
