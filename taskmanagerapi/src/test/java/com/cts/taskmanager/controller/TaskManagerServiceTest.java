package com.cts.taskmanager.controller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.cts.taskmanager.TaskManagerAppApplication;
import com.cts.taskmanager.bean.TaskBean;
import com.cts.taskmanager.bean.TaskSearchBean;
import com.cts.taskmanager.jpa.entity.Task;
import com.cts.taskmanager.jpa.repository.TaskRepository;
import com.cts.taskmanager.service.TaskManagerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskManagerAppApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskManagerServiceTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	TaskManagerService serivce;

	@SuppressWarnings("unchecked")
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = (HttpMessageConverter<Object>) Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	private List<Task> tasks = new ArrayList<Task>();
	private Task parent = null;
	private LocalDate startDate = LocalDate.now();
	private LocalDate endDate = LocalDate.now().plusDays(5);
	private TaskBean updateTask;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.taskRepository.deleteAll();
		parent = this.taskRepository.save(new Task("Task1", 10, startDate, endDate, null));
		updateTask = this.serivce.findById(parent.getId());
		tasks.add(parent);
		tasks.add(this.taskRepository.save(new Task("Task2", 10, startDate, endDate, parent)));
		tasks.add(this.taskRepository.save(new Task("Task3", 8, startDate, endDate, parent)));
	}

	@Test
	public void getTaskByTaskIdTest() throws Exception {
		mockMvc.perform(get("/taskmanager/api/tasks/" + this.tasks.get(0).getId())).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.taskId", is(this.tasks.get(0).getId().intValue())))
				.andExpect(jsonPath("$.taskName", is("Task1")))
				.andExpect(jsonPath("$.startDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(startDate))))
				.andExpect(jsonPath("$.endDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(endDate))))
				.andExpect(jsonPath("$.priority", is(10))).andExpect(jsonPath("$.parentTaskId", nullValue()));
	}

	@Test
	public void readAllTasksTest() throws Exception {
		mockMvc.perform(get("/taskmanager/api/tasks")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	public void createTaskTest() throws Exception {
		String jsonTask = json(new TaskBean(null, "Task4", startDate, endDate, 7, null));
		this.mockMvc.perform(post("/taskmanager/api/tasks").contentType(contentType).content(jsonTask))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.taskName", is("Task4")))
				.andExpect(jsonPath("$.startDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(startDate))))
				.andExpect(jsonPath("$.endDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(endDate))))
				.andExpect(jsonPath("$.priority", is(7))).andExpect(jsonPath("$.parentTaskId", nullValue()));
	}

	@Test
	public void updateTaskTest() throws Exception {
		updateTask.setTaskName("Parent Task");
		String jsonTask = json(updateTask);
		this.mockMvc.perform(put("/taskmanager/api/tasks").contentType(contentType).content(jsonTask))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.taskName", is("Parent Task")))
				.andExpect(jsonPath("$.startDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(startDate))))
				.andExpect(jsonPath("$.endDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(endDate))))
				.andExpect(jsonPath("$.priority", is(10)));
	}

	@Test
	public void taskSearchByParentTaskName() throws Exception {
		String searchJson = json(new TaskSearchBean(null, "Task1", null, null, null, null));
		this.mockMvc.perform(post("/taskmanager/api/tasks/search").contentType(contentType).content(searchJson))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].taskName", is("Task2")))
				.andExpect(jsonPath("$[0].startDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(startDate))))
				.andExpect(jsonPath("$[0].endDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(endDate))))
				.andExpect(jsonPath("$[0].priority", is(10)));
	}

	@Test
	public void taskSearchByTaskName() throws Exception {
		String searchJson = json(new TaskSearchBean("Task1", null, null, null, null, null));
		this.mockMvc.perform(post("/taskmanager/api/tasks/search").contentType(contentType).content(searchJson))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].taskName", is("Task1")))
				.andExpect(jsonPath("$[0].startDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(startDate))))
				.andExpect(jsonPath("$[0].endDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(endDate))))
				.andExpect(jsonPath("$[0].priority", is(10))).andExpect(jsonPath("$[0].parentTaskId", nullValue()));
	}

	@Test
	public void taskSearchByTaskAndParentTaskName() throws Exception {
		String searchJson = json(new TaskSearchBean("Task2", "Task1", null, null, null, null));
		this.mockMvc.perform(post("/taskmanager/api/tasks/search").contentType(contentType).content(searchJson))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].taskName", is("Task2")))
				.andExpect(jsonPath("$[0].startDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(startDate))))
				.andExpect(jsonPath("$[0].endDate", is(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(endDate))))
				.andExpect(jsonPath("$[0].priority", is(10)));
	}
	

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
