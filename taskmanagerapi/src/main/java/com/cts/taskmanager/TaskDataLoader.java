package com.cts.taskmanager;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cts.taskmanager.jpa.entity.Task;
import com.cts.taskmanager.jpa.repository.TaskRepository;

@Component
public class TaskDataLoader implements ApplicationRunner {

	@Autowired
	TaskRepository taskRepository;
	private Task parent = null;
	private LocalDate startDate = LocalDate.now();
	private LocalDate endDate = LocalDate.now().plusDays(5);
	
	public void run(ApplicationArguments args) {
		this.taskRepository.deleteAll();
		parent = this.taskRepository.save(new Task("Parent Task", 10, startDate, endDate, null));
		this.taskRepository.save(new Task("Vel Task", 11, startDate, endDate, parent));
		this.taskRepository.save(new Task("Kumar Task", 20, startDate, endDate, null));
		this.taskRepository.save(new Task("Vijay Task", 30, startDate, endDate, parent));
		this.taskRepository.save(new Task("Ravi Task", 22, startDate, endDate, null));
	}
}
