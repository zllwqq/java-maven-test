package com.zlstudy.service;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.zlstudy.entity.LoginLog;

@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class LoginLogServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private LoginLogService loginLogService;

	public LoginLogService getLoginLogService() {
		return loginLogService;
	}

	@Autowired
	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}
	
	@Test
	@Rollback(false)
	public void saveTest() {
		LoginLog loginLog = new LoginLog();
		loginLog.setUserId(13323712806L);
		Date now = new Date();
		loginLog.setLoginDate(now);
		loginLog.setLoginTime(now);
		loginLog.setCreateTime(now);
		
		loginLogService.save(loginLog);
	}
	
	@Test
	public void getTest() {
		LoginLog loginLog = loginLogService.get(2);
		System.out.println(loginLog.getLoginDate());
		System.out.println(loginLog.getCreateTime().toString());
		logger.info(loginLog.getLoginDate().toString());
		logger.info(loginLog.getLoginTime().toString());
	}
	
	@Test
	public void findTest() {
//		DateFormat sdf = new SimpleDateFormat("2016-12-05");
		LoginLog loginLog = loginLogService.findByDateAndUser(13323712806L, "2016-12-05");
		System.out.println(loginLog);
		logger.info(loginLog.getLoginDate().toString());
		logger.info(loginLog.getLoginTime().toString());
	}
	
	@Test
	@Rollback(false)
	public void updateTest() {
		int result = loginLogService.updateLoginTime(3, new Date());
		System.out.println(result);
	}
}
