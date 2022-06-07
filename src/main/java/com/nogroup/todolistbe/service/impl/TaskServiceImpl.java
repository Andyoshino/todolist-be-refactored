package com.nogroup.todolistbe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nogroup.todolistbe.entity.ActionEvent;
import com.nogroup.todolistbe.exception.CustomException;
import com.nogroup.todolistbe.exception.ErrorCode;
import com.nogroup.todolistbe.repository.TaskRepository;
import com.nogroup.todolistbe.repository.TaskRepositoryCustom;
import com.nogroup.todolistbe.service.ActionEventProducerService;
import com.nogroup.todolistbe.web.model.request.AddTaskWebRequest;
import com.nogroup.todolistbe.web.model.request.EditTaskWebRequest;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import com.nogroup.todolistbe.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.nogroup.todolistbe.service.TaskService;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepositoryCustom taskRepositoryCustom;
  private final TaskRepository taskRepository;
  private final ActionEventProducerService actionEventProducerService;


  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ActionEventConstructorDTO {
    private String action;
    private String performer;
    private Task oldTask;
    private Task newTask;
  }

  private ActionEvent constructActionEvent(ActionEventConstructorDTO actionEventDTO) {
    ActionEvent actionEvent = ActionEvent
        .builder()
        .action(actionEventDTO.getAction())
        .updatedBy(actionEventDTO.getPerformer())
        .build();

    if (actionEventDTO.getAction().equals("UPDATE")) {
      actionEvent.setNewValue(actionEventDTO.getNewTask());
      actionEvent.setOldValue(actionEventDTO.getOldTask());
      List<String> updatedFields = new ArrayList<>();
      if (!actionEventDTO.getOldTask().getTask().equals(actionEventDTO.getNewTask().getTask()))
        updatedFields.add("task");

      if (!actionEventDTO.getOldTask().getDetail().equals(actionEventDTO.getNewTask().getDetail()))
        updatedFields.add("detail");

      if (!actionEventDTO.getOldTask()
          .getSchedule()
          .equals(actionEventDTO.getNewTask().getSchedule()))
        updatedFields.add("schedule");

      actionEvent.setUpdatedFields(updatedFields);
    }

    return actionEvent;
  }

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
  public Mono<Task> addTask(AddTaskWebRequest addTaskWebRequest, String performer) {
    Task task = new Task();
    BeanUtils.copyProperties(addTaskWebRequest, task);
    return taskRepository
        .save(task)
        .map(savedTask -> {
          try {
            return publishActionEvent(ActionEventConstructorDTO.builder()
                .action("INSERT")
                .performer(performer)
                .newTask(savedTask)
                .build());
          } catch (JsonProcessingException e) {
            e.printStackTrace();
            return savedTask;
          }
        });
  }

  @Override
  public Mono<Task> deleteTask(String id, String performer) {
    return taskRepository
        .findById(id)
        .switchIfEmpty(Mono.defer(() -> Mono.error(new CustomException(ErrorCode.TASK_NOT_FOUND))))
        .flatMap(task -> taskRepository.deleteById(id).thenReturn(task))
        .map(deletedTask -> {
          try {
            return publishActionEvent(ActionEventConstructorDTO.builder()
                .action("DELETE")
                .performer(performer)
                .newTask(deletedTask)
                .build());
          } catch (JsonProcessingException e) {
            e.printStackTrace();
            return deletedTask;
          }
        });
  }

  @Override
  public Mono<Task> editTask(String id, EditTaskWebRequest editTaskWebRequest, String performer) {
    Task task = new Task();
    BeanUtils.copyProperties(editTaskWebRequest, task);
    task.setId(id);

    ActionEventConstructorDTO actionEventConstructorDTO = ActionEventConstructorDTO.builder()
        .action("UPDATE")
        .performer(performer)
        .newTask(task)
        .build();

    return taskRepository
        .findById(id)
        .switchIfEmpty(Mono.defer(() -> Mono.error(new CustomException(ErrorCode.TASK_NOT_FOUND))))
        .flatMap(oldTask -> {
          actionEventConstructorDTO.setOldTask(oldTask);
          return taskRepository.save(task);
        })
        .map(savedTask -> {
          try {
            return publishActionEvent(actionEventConstructorDTO);
          } catch (JsonProcessingException e) {
            e.printStackTrace();
            return savedTask;
          }
        });
  }

  private Task publishActionEvent(ActionEventConstructorDTO actionEventDTO)
      throws JsonProcessingException {
    actionEventProducerService.publishActionEvent(constructActionEvent(actionEventDTO));
    return actionEventDTO.getNewTask();
  }

}
