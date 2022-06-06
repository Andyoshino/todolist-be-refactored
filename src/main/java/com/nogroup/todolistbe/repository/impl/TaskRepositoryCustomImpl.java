package com.nogroup.todolistbe.repository.impl;

import com.nogroup.todolistbe.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import com.nogroup.todolistbe.repository.TaskRepositoryCustom;
import com.nogroup.todolistbe.web.model.request.GetTaskListWebRequest;

@Service
@RequiredArgsConstructor
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  @Override
  public Flux<Task> getTaskPaged(GetTaskListWebRequest request) {
    Sort.Direction sortDirection = Sort.Direction.ASC;
    if (StringUtils.hasText(request.getSortDir()) && request.getSortDir().equals("DESC"))
      sortDirection = Sort.Direction.DESC;
    String filter = "";
    if (StringUtils.hasText(request.getFilter()))
      filter = request.getFilter();
    String sortBy = "id";
    if (StringUtils.hasText(request.getSortBy()))
      sortBy = request.getSortBy();

    Query query = new Query();
    query.addCriteria(Criteria.where("task").regex(".*" + filter + ".*", "i"))
        .with(PageRequest.of(request.getPage()-1, request.getPageSize()))
        .with(Sort.by(sortDirection, sortBy));

    return reactiveMongoTemplate.find(query, Task.class);
  }
}
