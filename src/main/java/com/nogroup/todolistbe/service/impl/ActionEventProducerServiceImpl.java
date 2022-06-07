package com.nogroup.todolistbe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nogroup.todolistbe.entity.ActionEvent;
import com.nogroup.todolistbe.helper.JsonHelper;
import com.nogroup.todolistbe.service.ActionEventProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class ActionEventProducerServiceImpl implements ActionEventProducerService {

  @Autowired
  KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  JsonHelper jsonHelper;

  @Override
  public void publishActionEvent(ActionEvent actionEvent) throws JsonProcessingException {
    ListenableFuture<SendResult<String,String>> listenableFuture = kafkaTemplate.send("action-events", jsonHelper.toJson(actionEvent));
    listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
      @Override
      public void onFailure(Throwable ex) {
        log.error("Publishing message failed, with the exception {}", ex.getMessage());
      }

      @Override
      public void onSuccess(SendResult<String, String> result) {
        try {
          log.info("Message sent successfully for {}, in partition {}", jsonHelper.toJson(actionEvent), result.getRecordMetadata().partition());
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
