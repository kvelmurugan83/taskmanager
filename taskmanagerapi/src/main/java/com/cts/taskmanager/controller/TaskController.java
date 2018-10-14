package com.cts.taskmanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.taskmanager.bean.TaskBean;
import com.cts.taskmanager.bean.TaskSearchBean;
import com.cts.taskmanager.service.TaskManagerService;

@RestController()
@RequestMapping("taskmanager/api/tasks")
@CrossOrigin
public class TaskController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	TaskManagerService service;
	
	@GetMapping()
	public List<TaskBean> retrieveAllTasks() {
		LOG.info("retrieveAllTasks");
		return service.loadAllTasks();
	}
	
	@GetMapping("/{taskId}")
	public TaskBean retrieveTaskById(@PathVariable Long taskId) {
		LOG.info("retrive Task By Id : " + taskId);
		return service.findById(taskId);
	}
	
	@DeleteMapping("/{taskId}")
	public void deleteTask(@PathVariable Long taskId) {
		LOG.info("Delete Task with ID " + taskId);
		service.deleteTask(taskId);
	}
	
	@PostMapping()
	public TaskBean createTask(@RequestBody TaskBean task) {
		LOG.info("Create a new Task " + task );
		return service.createTask(task);
	}
	
	@PostMapping("/search")
	public Iterable<TaskBean> searchTask(@RequestBody TaskSearchBean taskSearchBean) {
		return service.searchTask(taskSearchBean);
	}
	
	@PutMapping()
	public TaskBean updateTask(@RequestBody TaskBean task) {
		LOG.info("update Task " + task );
		return service.updateTask(task);
	}
}
