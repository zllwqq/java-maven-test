package com.zlstudy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlstudy.dao.LoginLogDao;
import com.zlstudy.entity.LoginLog;
import com.zlstudy.service.LoginLogService;

@Service
@Transactional
public class LoginLogServiceImpl implements LoginLogService {
	
	private LoginLogDao loginLogDao;

	public LoginLogDao getLoginLogDao() {
		return loginLogDao;
	}

	@Autowired
	public void setLoginLogDao(LoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}
	
	public void save(LoginLog loginLog) {
		loginLogDao.save(loginLog);
	}
	
	public LoginLog get(Integer id) {
		return loginLogDao.get(id);
	}
	
	public LoginLog findByDateAndUser(Long userId, Date loginDate) {
		String hql = "from LoginLog where userId = ? and loginDate = ?";
		return loginLogDao.findUnique(hql, userId, loginDate);
	}
	
	public LoginLog findByDateAndUser(Long userId, String loginDate) {
		String sql = "select * from login_log where userId = ? and loginDate = ?";
		Map<String, Type> scalar = new HashMap<String, Type>();
		scalar.put("userId", StandardBasicTypes.LONG);//虽然sql语句为select *，但是scalar导致查询结果类中只有userId有值，其它为null
 		return loginLogDao.findUnique(LoginLog.class, scalar, sql, userId, loginDate);
	}
	
	public int updateLoginTime(Integer id, Date loginTime) {
		String sql = "update login_log set loginTime = ? where id = ?";
		return loginLogDao.excuteExecute(sql, loginTime, id);
	}
	
	public int updateLoginTime(Integer id, String loginTime) {
		String sql = "update login_log set loginTime = ? where id = ?";
		return loginLogDao.excuteExecute(sql, loginTime, id);
	}
}
