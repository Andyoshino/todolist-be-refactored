package com.nogroup.todolistbe.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ActionEventConsumerService {
  public void onMessage(String event) throws JsonProcessingException;
}
