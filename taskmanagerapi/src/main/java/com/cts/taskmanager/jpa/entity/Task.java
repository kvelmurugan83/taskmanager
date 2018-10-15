package com.cts.taskmanager.jpa.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Task implements Serializable {

	private static final long serialVersionUID = 20180608;

	public Task() {
	}

	public Task(String taskName, Integer priority, LocalDate startDate, LocalDate endDate, Task task) {
		this.taskName = taskName;
		this.priority = priority;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parent = task;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TASK_ID")
	private Long id;

	@Column(name = "TASK_NAME")
	private String taskName;

	@Column(name = "START_DATE")
	private LocalDate startDate;

	@Column(name = "END_DATE")
	private LocalDate endDate;

	@Column(name = "PRIORITY")
	private Integer priority;
	
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "PARENT_ID")
	private Task parent;
	
	@Column(name = "END")
	private Boolean end = false;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
