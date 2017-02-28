package com.zlstudy.service;

import java.util.Date;

import com.zlstudy.entity.LoginLog;

public interface LoginLogService {

	public void save(LoginLog loginLog);
	
	public LoginLog get(Integer id);
	
	public LoginLog findByDateAndUser(Long userId, Date loginDate);
	
	public LoginLog findByDateAndUser(Long userId, String loginDate);
	
	public int updateLoginTime(Integer id, Date loginTime);
	
	public int updateLoginTime(Integer id, String loginTime);
}
