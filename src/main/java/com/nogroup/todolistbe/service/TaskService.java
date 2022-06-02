package com.nogroup.todolistbe.service;

import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;
import com.nogroup.todolistbe.web.model.response.GetTaskListWebResponse;
import reactor.core.publisher.Mono;

public interface TaskService {
  Mono<GetTaskListWebResponse> getTaskList(GetTaskListWebRequest request);
}
