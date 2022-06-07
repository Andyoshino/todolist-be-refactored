package com.nogroup.todolistbe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nogroup.todolistbe.entity.ActionEvent;

public interface ActionEventProducerService {
  public void publishActionEvent(ActionEvent actionEvent) throws JsonProcessingException;
}
