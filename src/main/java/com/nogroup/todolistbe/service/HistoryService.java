package com.nogroup.todolistbe.service;

import com.nogroup.todolistbe.web.model.request.GetHistoryWebRequest;
import com.nogroup.todolistbe.web.model.response.GetHistoryWebResponse;
import reactor.core.publisher.Mono;

public interface HistoryService {
  Mono<GetHistoryWebResponse> getHistory(GetHistoryWebRequest getHistoryWebRequest);
}
