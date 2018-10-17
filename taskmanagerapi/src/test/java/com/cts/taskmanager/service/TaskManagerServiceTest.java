package com.cts.taskmanager.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;

import com.cts.taskmanager.bean.TaskBean;
import com.cts.taskmanager.jpa.entity.Task;
import com.cts.taskmanager.jpa.repository.TaskRepository;

public class TaskManagerServiceTest {

	TaskRepository repository = mock(TaskRepository.class);

	TaskManagerService service;

	List<Task> allTask = new ArrayList<Task>();
	Task t;
	TaskBean tb;

	@Before
	public void setup() {
		service = new TaskManagerService(repository);
		t = new Task("name", 10, null, null, null);
		t.setId(Long.valueOf(1));
		tb = new TaskBean(Long.valueOf(1), "name", null, null, 10, null);
		allTask.add(t);
	}

	@Test
	public void loadAllTasks() {
		when(repository.findAll()).thenReturn(allTask);

		List<TaskBean> t = service.loadAllTasks();
		assertEquals(t.size(), 1);
		t.forEach(task -> {
			assertEquals(task.getTaskName(), "name");
			assertEquals(task.getPriority(), new Integer(10));
		});
	}
	
	@Test
	public void tesFindByID() {
		Long taskId = new Long(10);
		when(repository.findById(taskId)).thenReturn(Optional.of(t));
		
		TaskBean actual = service.findById(taskId);
		
		verify(repository).findById(taskId);
		assertEquals(actual.getTaskName(), "name");
		assertEquals(actual.getTaskName(), "name");
		assertEquals(actual.getPriority(),  new Integer(10));
	}
	
	@Test 
	public void testCreateTask() {
		when(repository.save(t)).thenReturn(t);
		
		TaskBean actual = service.createTask(tb);
		
		assertEquals(actual.getTaskName(), "name");
		assertEquals(actual.getTaskId(), new Long(1));
		assertEquals(actual.getPriority(),  new Integer(10));
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testUpdateTaskFail() {
		when(repository.existsById(new Long(1))).thenReturn(false);

		service.updateTask(tb);
	}
	
	@Test
	public void testUpdateTask() {
		Long taskId = new Long(1);
		when(repository.existsById(taskId)).thenReturn(true);
		when(repository.save(t)).thenReturn(t);
		
		TaskBean actual = service.updateTask(tb);
		
		verify(repository).existsById(taskId);
		verify(repository).save(t);
		assertEquals(actual.getTaskName(), "name");
		assertEquals(actual.getTaskId(), new Long(1));
		assertEquals(actual.getPriority(),  new Integer(10));
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteTaskFail() {
		Long taskId = new Long(1);
		when(repository.existsById(taskId)).thenReturn(false);

		service.deleteTask(taskId);
	}
	
	@Test
	public void testDeleteTask() {
		Long taskId = new Long(1);
		when(repository.existsById(taskId)).thenReturn(true);
		
		service.deleteTask(taskId);
		
		verify(repository).existsById(taskId);
		verify(repository).deleteById(taskId);
	}
}
