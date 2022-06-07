package com.nogroup.todolistbe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class WebConfiguration {

  @Bean
  public Scheduler commonScheduler() {
    return Schedulers.newSingle("COMMON");
  }

}
