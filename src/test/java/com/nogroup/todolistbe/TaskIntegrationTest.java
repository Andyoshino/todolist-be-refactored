package com.nogroup.todolistbe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nogroup.todolistbe.entity.Response;
import com.nogroup.todolistbe.entity.Task;
import com.nogroup.todolistbe.exception.ErrorCode;
import com.nogroup.todolistbe.repository.TaskRepository;
import com.nogroup.todolistbe.web.model.request.EditTaskWebRequest;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Date;

public class TaskIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private TaskRepository taskRepository;

  @AfterEach
  void reset() {
    taskRepository.deleteAll().block();
  }

  @Test
  public void itShouldReturnTaskListCorrectlyFromDB() {
    taskRepository.save(Task.builder()
        .task("Task 1")
        .detail("Detail 1")
        .schedule(new Date())
        .build()).block();
    taskRepository.save(Task.builder()
        .task("Task 2")
        .detail("Detail 2")
        .schedule(new Date())
        .build()).block();
    taskRepository.save(Task.builder()
        .task("Task 3")
        .detail("Detail 3")
        .schedule(new Date())
        .build()).block();

    webTestClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/api/task")
            .queryParam("pageSize", 10)
            .queryParam("page", 1)
            .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(mapAndAssert(new TypeReference<Response<GetTaskListWebResponse>>() {
        }, response -> {
          Assertions.assertNotNull(response.getData());
          Assertions.assertEquals(3, response.getData().getTasks().size());
        }));
  }

  @Test
  public void itShouldReturnBadRequest() {
    webTestClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/api/task")
            .queryParam("pageSize", 0)
            .queryParam("page", 0)
            .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(mapAndAssert(new TypeReference<Response<GetTaskListWebResponse>>() {
        }, response -> {
          Assertions.assertNull(response.getData());
          Assertions.assertEquals(2, response.getErrors().size());
        }));
  }

  @Test
  public void itShouldDeleteDataSuccesfullyFromDB() {
    Task task =
        Task.builder().id("123456").task("Task 1").detail("Detail 1").schedule(new Date()).build();

    taskRepository.save(task).block();

    webTestClient
        .delete()
        .uri(uriBuilder -> uriBuilder.path("/api/task")
            .queryParam("id", "123456")
            .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(mapAndAssert(new TypeReference<Response<Task>>() {
        }, response -> {
          Assertions.assertNotNull(response.getData());
          Assertions.assertEquals(task.getTask(), response.getData().getTask());
          Assertions.assertEquals(task.getId(), response.getData().getId());
          Assertions.assertEquals(task.getDetail(), response.getData().getDetail());
        }));
  }

  @Test
  public void itShouldReturnErrorNotFound() {
    webTestClient
        .delete()
        .uri(uriBuilder -> uriBuilder.path("/api/task")
            .queryParam("id", "123456")
            .build())
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .consumeWith(mapAndAssert(new TypeReference<Response<Task>>() {
        }, response -> {
          Assertions.assertEquals(ErrorCode.TASK_NOT_FOUND.errorMessage,
              response.getErrors().get(0));
        }));
  }

  @Test
  public void itShouldEditDataSuccessfully() throws JsonProcessingException {
    Task task =
        Task.builder().id("123456").task("Task 1").detail("Detail 1").schedule(new Date()).build();

    taskRepository.save(task).block();

    EditTaskWebRequest editTaskWebRequest =
        EditTaskWebRequest.builder().task("ABC").detail("Detail ABC").schedule(new Date()).build();

    webTestClient
        .put()
        .uri(uriBuilder -> uriBuilder.path("/api/task")
            .queryParam("id", "123456")
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(jsonHelper.toJson(editTaskWebRequest)))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(mapAndAssert(new TypeReference<Response<Task>>() {
        }, response -> {
          Assertions.assertNotNull(response.getData());
          Assertions.assertEquals(editTaskWebRequest.getTask(), response.getData().getTask());
          Assertions.assertEquals(editTaskWebRequest.getDetail(), response.getData().getDetail());

          Task editedTask = taskRepository.findById("123456").block();
          Assertions.assertEquals(editedTask, response.getData());
        }));
  }

}
