package com.cts.taskmanager.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.taskmanager.jpa.entity.ParentTask;
import com.cts.taskmanager.jpa.entity.Task;
import com.cts.taskmanager.jpa.repository.ParentTaskRepository;

/****
 * 
 * @author Velmurugan Kandasamy
 *
 */

@RestController()
@RequestMapping("taskmanager/api/parentTask")
public class ParentTaskController {

	
	private static final Logger LOG = LoggerFactory.getLogger(ParentTaskController.class);

	@Autowired
	ParentTaskRepository repository;
	
	@GetMapping("/parenttasks")
	public Iterable<ParentTask> retrieveAllParentTasks() {
		LOG.info("retrieveAllParentTasks");
		return repository.findAll();
	}
	
	@GetMapping("/parenttasks/{taskId}")
	public ParentTask retrieveTaskById(@PathVariable Long taskId) {
		LOG.info("retrive Parent Task By Id : " + taskId);
		Optional<ParentTask> task = repository.findById(taskId);
		if (! task.isPresent()) {
			LOG.error("ParentTask Not Found for Id "+ taskId );
			throw new EntityNotFoundException("Parent Taks Not Found for Id " + taskId );
		}
		return task.get();
	}
	
	@GetMapping("/parenttasks/retrieveTasksByParentId/{parentTaskId}")
	public List<Task> retrieveAllSubTask(@PathVariable Long parentTaskId) {
		LOG.info("retrive all Task By parent task Id  : " + parentTaskId);
		Optional<ParentTask> task = repository.findById(parentTaskId);
		if (! task.isPresent()) {
			LOG.error("ParentTask Not Found for Id "+ parentTaskId );
			throw new EntityNotFoundException("Parent Taks Not Found for Id " + parentTaskId );
		}
		return task.get().getTasks();
	}
	
	@DeleteMapping("/parenttasks/{taskId}")
	public void deleteTask(@PathVariable Long taskId) {
		LOG.info("Delete Task with ID " + taskId);
		if (!repository.existsById(taskId) ) {
			LOG.error("Parent Task Not Found for Id "+ taskId );
			throw new EntityNotFoundException("Parent Taks Not Found for Id " + taskId );
		}
		repository.deleteById(taskId);
	}
	
	@PostMapping("/parenttasks")
	public ParentTask createTask(@RequestBody ParentTask task) {
		LOG.info("Create a new Parent Task " + task );
		return repository.save(task);
	}
	
	@PutMapping("/parenttasks/{taskId}")
	public ParentTask updateTask(@RequestBody ParentTask task, @PathVariable Long taskId) {
		LOG.info("update Parent Task " + task );
		if (!repository.existsById(taskId) ) {
			LOG.error("Parent Task Not Found for Id "+ taskId );
			throw new EntityNotFoundException("Parent Taks Not Found for Id " + taskId );
		}
		return repository.save(task);
	}
	
}
