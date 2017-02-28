package com.zlstudy.service;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.zlstudy.dao.hibernate.Page;
import com.zlstudy.entity.User;

@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Test
	public void findPageTest() {
		Page<User> page = new Page<User>();
		page.setPageNo(1);
		page.setPageSize(10);
		
		Page<User> result = userService.findPage(page);
		User user = result.getResult().get(0);
		System.out.println(user.getSchoolName());
//		System.out.println(result.getResult().get(0).get("userId").getClass());
//		System.out.println(result.getResult().get(0).get("name"));
//		System.out.println(result.getResult().get(0).get("createTime").getClass());
//		System.out.println(result.getResult().get(0).get("expireTime").getClass());
	}
	
	@Test
	public void findPageNullClaszTest() {
		Page page = new Page();
		page.setPageNo(1);
		page.setPageSize(10);
		
		Page result = userService.findPageNullClasz(page);
		Object[] o = (Object[]) result.getResult().get(0);
		System.out.println(o[0]);
		System.out.println(o[1]);
	}
	
	@Test
	public void findTest() {
		long begin = System.currentTimeMillis();
		List<User> users = userService.find();
		long end = System.currentTimeMillis();
		logger.info("cost "+(end-begin)+"ms");
	}
	
	@Test
	public void findUserIdTest() {
		long begin = System.currentTimeMillis();
		long userId = 13323712806L;
		List<User> users = userService.find(userId);
		long end = System.currentTimeMillis();
		logger.info("cost "+(end-begin)+"ms");
	}
	
	@Test
	public void testJdbc() {
		long begin = System.currentTimeMillis();
		userService.getJdbc();
		long end = System.currentTimeMillis();
		logger.info("cost "+(end-begin)+"ms");
	}
}
