package com.nogroup.todolistbe.service.impl;

import com.nogroup.todolistbe.entity.ActionEvent;
import com.nogroup.todolistbe.repository.ActionEventRepository;
import com.nogroup.todolistbe.repository.ActionEventRepositoryCustom;
import com.nogroup.todolistbe.service.HistoryService;
import com.nogroup.todolistbe.web.model.request.GetHistoryWebRequest;
import com.nogroup.todolistbe.web.model.response.GetHistoryWebResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {

  private final ActionEventRepositoryCustom actionEventRepositoryCustom;

  @Override
  public Mono<GetHistoryWebResponse> getHistory(GetHistoryWebRequest getHistoryWebRequest) {
    return actionEventRepositoryCustom
        .getHistoryPaged(getHistoryWebRequest)
        .collectList()
        .map(this::constructGetHistoryWebResponse);
  }

  private GetHistoryWebResponse constructGetHistoryWebResponse(List<ActionEvent> actionEventList) {
    return GetHistoryWebResponse
        .builder()
        .actionEvents(actionEventList)
        .build();
  }
}
