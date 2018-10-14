package com.cts.taskmanager.bean;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TaskSearchBean implements Serializable {

	private static final long serialVersionUID = 20180608;
	
	public TaskSearchBean() {}
	
	public TaskSearchBean(String taskName, String parentTaskName, Integer priorityFrom, Integer priorityTo, LocalDate startDate, LocalDate endDate) {
		this.taskName = taskName;
		this.parentTaskName = parentTaskName;
		this.priorityFrom = priorityFrom;
		this.priorityTo = priorityTo;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	private String taskName;
	private String parentTaskName;
	private Integer priorityFrom;
	private Integer priorityTo;
	private LocalDate startDate;
	private LocalDate endDate;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
