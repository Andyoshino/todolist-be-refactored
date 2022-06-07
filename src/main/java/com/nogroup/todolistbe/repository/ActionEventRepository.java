package com.nogroup.todolistbe.repository;

import com.nogroup.todolistbe.entity.ActionEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ActionEventRepository extends ReactiveMongoRepository<ActionEvent, String> {
}
