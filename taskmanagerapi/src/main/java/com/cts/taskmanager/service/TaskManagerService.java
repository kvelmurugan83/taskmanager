package com.cts.taskmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.taskmanager.bean.TaskBean;
import com.cts.taskmanager.bean.TaskSearchBean;
import com.cts.taskmanager.jpa.entity.Task;
import com.cts.taskmanager.jpa.repository.TaskRepository;
import com.cts.taskmanager.jpa.repository.TaskSearchRepository;

@Service
public class TaskManagerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TaskManagerService.class);
	
	public TaskManagerService() {
		
	}
	
	public TaskManagerService(TaskRepository repository) {
		this.repository = repository;
	}
	
	@Autowired
	TaskRepository repository;
	
	@Autowired
	TaskSearchRepository searchRepository;

	public List<TaskBean> loadAllTasks() {
		LOG.info("loadAllTasks : ");
		List<TaskBean> result = new ArrayList<TaskBean>();
		repository.findAll().forEach(
			task -> {
				result.add(transaformTaskEntityToDomain(task));
			}
		);
		return result;
	}
	
	public TaskBean findById(Long taskId) {
		LOG.info("retrive Task By Id : " + taskId);
		Optional<Task> task = repository.findById(taskId);
		if (! task.isPresent()) {
			LOG.error("Task Not Found for Id "+ taskId );
			throw new EntityNotFoundException("Taks Not Found for Id " + taskId );
		}
		return transaformTaskEntityToDomain(task.get());
	}
	
	public TaskBean updateTask(TaskBean task) {
		LOG.info("Update Task: " + task);
		if (repository.existsById(task.getTaskId()) ) {
			Task entity = this.transformTaskBeantoEntity(task);
			return this.transaformTaskEntityToDomain(repository.save(entity));
		} else {
			throw new EntityNotFoundException("Taks Not Found for Id " + task.getTaskId() );
		}
	}
	
	public TaskBean createTask(TaskBean task) {
		LOG.info("Insert Task: " + task);
		Task entity = this.transformTaskBeantoEntity(task);
		return this.transaformTaskEntityToDomain(repository.save(entity));
	}
	
	public void deleteTask(Long taskId) {
		LOG.info("Delete Task with ID " + taskId);
		if (!repository.existsById(taskId) ) {
			LOG.error("Task Not Found for Id "+ taskId);
			throw new EntityNotFoundException("Taks Not Found for Id " + taskId );
		}
		repository.deleteById(taskId);
	}
	
	public List<TaskBean> searchTask(TaskSearchBean taskSearchBean) {
		LOG.info("Task Search " + taskSearchBean);
		List<TaskBean> result = new ArrayList<TaskBean>();
		searchRepository.searchTask(taskSearchBean).forEach(
			task -> {
				result.add(transaformTaskEntityToDomain(task));
			}
		);
		return result;
	}
	
	private TaskBean transaformTaskEntityToDomain(Task task) {
		TaskBean bean = new TaskBean();
		bean.setTaskId(task.getId());
		bean.setTaskName(task.getTaskName());
		bean.setPriority(task.getPriority());
		bean.setEndDate(task.getEndDate());
		bean.setStartDate(task.getStartDate());
		if (task.getParent() != null) {
			bean.setParentTaskId(task.getParent().getId());
			bean.setParentTaskName(task.getParent().getTaskName());
		} else {
			bean.setParentTaskName("");
		}
		bean.setEnd(task.getEnd());
		return bean;
	}
	
	private Task transformTaskBeantoEntity(TaskBean task) {
		Task t= new Task();
		if (task.getTaskId() != null && task.getTaskId() > 0 ) {
			t.setId(task.getTaskId());
		}
		t.setTaskName(task.getTaskName());
		t.setPriority(task.getPriority());
		t.setEndDate(task.getEndDate());
		t.setStartDate(task.getStartDate());
		if (task.getParentTaskId() != null && task.getParentTaskId() > 0 ) {
			Optional<Task> parent = repository.findById(task.getParentTaskId());
			t.setParent(parent.get());
		}
		t.setEnd(task.getEnd());
		return t;
	}
}
