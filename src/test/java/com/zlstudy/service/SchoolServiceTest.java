package com.zlstudy.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.zlstudy.entity.School;

@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class SchoolServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	private SchoolService schoolService;

	public SchoolService getSchoolService() {
		return schoolService;
	}

	@Autowired
	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	
	@Test
	public void getTest() {
		School school = schoolService.get(1932);
		System.out.println("学校id="+school.getId());
		System.out.println("学校那么name="+school.getName());
	}
	
	@Test
	public void loadTest() throws Exception{
		School school = schoolService.load(1932);
		System.out.println("学校id="+school.getId());
		System.out.println("学校那么name="+school.getName());
	}
	
	@Test
	public void saveBatchTest() {
		List<School> schools = new ArrayList<School>();
		
		School school1 = new School();
		school1.setName("批量添加测试学校1");
		school1.setType("高级测试中学");
		school1.setStudentTotal(0);
		schools.add(school1);
		
		School school2 = new School();
		school2.setName("批量添加测试学校1");
		school2.setType("高级测试中学");
		school2.setStudentTotal(0);
		schools.add(school2);
		
		schoolService.saveBatch(schools);
		
		System.out.println("test end------");
	}
	
	@Test
	public void findAllTest() {
		long begin = System.currentTimeMillis();
		List<School> schools = schoolService.getAll();
		long end = System.currentTimeMillis();
		logger.info("cost "+(end-begin)+"ms");
	}
}
