package com.nogroup.todolistbe.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfiguration {

  @Bean
  public NewTopic actionEvents() {
    return TopicBuilder.name("action-events").partitions(3).replicas(1).build();
  }

}
