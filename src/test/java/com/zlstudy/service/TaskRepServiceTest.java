package com.zlstudy.service;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.zlstudy.entity.TaskRep;

@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class TaskRepServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private TaskRepService taskRepService;

	public TaskRepService getTaskRepService() {
		return taskRepService;
	}

	@Autowired
	public void setTaskRepService(TaskRepService taskRepService) {
		this.taskRepService = taskRepService;
	}
	
	@Test
	public void findTaskRepsTest() {
		List<TaskRep> taskReps = this.taskRepService.findTaskReps();
		logger.info("attachment="+taskReps.get(0).getAttachment());
		logger.info("taskAttachment="+taskReps.get(0).getTaskAttachment());
	}
}
