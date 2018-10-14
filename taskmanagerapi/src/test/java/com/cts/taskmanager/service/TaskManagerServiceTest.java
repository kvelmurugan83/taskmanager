package com.cts.taskmanager.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

	@Before
	public void setup() {
	}

	@Test
	public void loadAllTasks() {
		service = new TaskManagerService(repository);
		allTask.add(new Task("name", 10, null, null, null));
		when(repository.findAll()).thenReturn(allTask);

		List<TaskBean> t = service.loadAllTasks();
		assertEquals(t.size(), 1);
		t.forEach(task -> {
			assertEquals(task.getTaskName(), "name");
			assertEquals(task.getPriority(), new Integer(10));
		});
	}
}
