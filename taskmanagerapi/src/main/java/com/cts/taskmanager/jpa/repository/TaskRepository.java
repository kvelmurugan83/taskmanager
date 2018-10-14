package com.cts.taskmanager.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.cts.taskmanager.jpa.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
