package com.nogroup.todolistbe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nogroup.todolistbe.entity.ActionEvent;
import com.nogroup.todolistbe.helper.JsonHelper;
import com.nogroup.todolistbe.repository.ActionEventRepository;
import com.nogroup.todolistbe.service.ActionEventConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActionEventConsumerServiceImpl implements ActionEventConsumerService {

  private final ActionEventRepository actionEventRepository;
  private final JsonHelper jsonHelper;

  @Override
  @KafkaListener(topics = {"action-events"})
  public void onMessage(String event) throws JsonProcessingException {
    log.info("An event received : {}", event);
    actionEventRepository.save(convertPayload(event)).block();
  }

  private ActionEvent convertPayload(String payload) throws JsonProcessingException {
    return jsonHelper.fromJson(payload, ActionEvent.class);
  }

}
