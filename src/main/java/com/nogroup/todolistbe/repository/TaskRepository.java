package com.nogroup.todolistbe.repository;

import com.nogroup.todolistbe.entity.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {

}
