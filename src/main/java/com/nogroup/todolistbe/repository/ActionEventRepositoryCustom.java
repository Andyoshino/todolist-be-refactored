package com.nogroup.todolistbe.repository;

import com.nogroup.todolistbe.entity.ActionEvent;
import com.nogroup.todolistbe.web.model.request.GetHistoryWebRequest;
import reactor.core.publisher.Flux;

public interface ActionEventRepositoryCustom {
  Flux<ActionEvent> getHistoryPaged(GetHistoryWebRequest getHistoryWebRequest);
}
