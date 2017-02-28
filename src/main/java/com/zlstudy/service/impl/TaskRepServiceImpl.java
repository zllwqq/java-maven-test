package com.zlstudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlstudy.dao.TaskRepDao;
import com.zlstudy.entity.TaskRep;
import com.zlstudy.service.TaskRepService;

@Service
@Transactional
public class TaskRepServiceImpl implements TaskRepService {
	
	private TaskRepDao taskRepDao;

	public TaskRepDao getTaskRepDao() {
		return taskRepDao;
	}

	@Autowired
	public void setTaskRepDao(TaskRepDao taskRepDao) {
		this.taskRepDao = taskRepDao;
	}
	
	public List<TaskRep> findTaskReps() {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.id, b.taskId, b.name, b.attachment, b.createTime, b.updateTime, b.status, a.title, a.requires, a.attachment as taskAttachment ");
		sb.append("from task_pub a, task_rep b ");
		sb.append("where a.chapterNo=? and a.sectionNo=? and b.userId=? and (a.type=2 or a.type=3 or a.type=4) and a.status=1 and a.id=b.taskId");
		return taskRepDao.find(TaskRep.class, sb.toString(), 1, 1, 13403788764L);
	}
}
