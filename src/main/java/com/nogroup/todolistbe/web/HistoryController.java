package com.nogroup.todolistbe.web;

import com.nogroup.todolistbe.entity.Response;
import com.nogroup.todolistbe.helper.ResponseHelper;
import com.nogroup.todolistbe.service.HistoryService;
import com.nogroup.todolistbe.web.model.request.GetHistoryWebRequest;
import com.nogroup.todolistbe.web.model.response.GetHistoryWebResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

  private final HistoryService historyService;
  private final Scheduler commonScheduler;

  @GetMapping
  public Mono<Response<GetHistoryWebResponse>> getHistory(@Valid GetHistoryWebRequest getHistoryWebRequest) {
    return historyService
        .getHistory(getHistoryWebRequest)
        .map(ResponseHelper::ok)
        .subscribeOn(commonScheduler);
  }

}
