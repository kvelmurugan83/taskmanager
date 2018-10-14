package com.cts.taskmanager.bean;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TaskBean {
	
	public TaskBean() {
		
	}
	
	public TaskBean(Long taskId, String taskName, LocalDate startDate, LocalDate endDate, Integer priority, Long parentId) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.parentTaskId = parentId;
	}
	
	private Long taskId;
	private String taskName;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer priority;
	private Long parentTaskId;
	private String parentTaskName;
}
