package com.cts.taskmanager.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.cts.taskmanager.jpa.entity.ParentTask;

public interface ParentTaskRepository extends CrudRepository<ParentTask, Long> {

}
