package com.nogroup.todolistbe.repository.impl;

import com.nogroup.todolistbe.entity.ActionEvent;
import com.nogroup.todolistbe.repository.ActionEventRepositoryCustom;
import com.nogroup.todolistbe.web.model.request.GetHistoryWebRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ActionEventRepositoryCustomImpl implements ActionEventRepositoryCustom {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  @Override
  public Flux<ActionEvent> getHistoryPaged(GetHistoryWebRequest getHistoryWebRequest) {
    Query query = new Query();
    query.with(PageRequest.of(getHistoryWebRequest.getPage()-1, getHistoryWebRequest.getPageSize()));
    return reactiveMongoTemplate.find(query, ActionEvent.class);
  }

}
