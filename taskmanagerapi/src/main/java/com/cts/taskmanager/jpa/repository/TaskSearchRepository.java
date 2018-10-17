package com.cts.taskmanager.jpa.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cts.taskmanager.bean.TaskSearchBean;
import com.cts.taskmanager.jpa.entity.Task;

@Repository
public class TaskSearchRepository {

	@PersistenceContext
	private EntityManager em;

	public Iterable<Task> searchTask(TaskSearchBean bean) {
		StringBuilder query = new StringBuilder();
		query.append("FROM Task WHERE ");
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		boolean operator = true;
		if (bean.getTaskName() != null) {
			query.append(" taskName = :taskName ");
			data.put("taskName", bean.getTaskName());
			operator = false;
		}
		if (bean.getParentTaskName() != null) {
			if (!operator) {
				query.append(" AND ");
			}
			query.append(" parent.taskName = :parentTaskName ");
			data.put("parentTaskName", bean.getParentTaskName());
			operator = false;
		}
		
		Integer from = 0;
		Integer to = 30;
		if (bean.getPriorityFrom() != null) {
			from = bean.getPriorityFrom();
		}
		if (bean.getPriorityTo() != null) {
			to = bean.getPriorityTo();
		}
		
		if (!operator) {
			query.append(" AND ");
		}
		query.append(" priority >= :priorityFrom  AND priority <= :priorityTo ");
		data.put("priorityFrom", from);
		data.put("priorityTo", to);
		operator = false;
		query.append( " ORDER BY id" );
		TypedQuery<Task> sql = em.createQuery(query.toString(), Task.class);
		data.forEach(
				(key, value) -> sql.setParameter(key, value)
		);
		return sql.getResultList();
	}
}
