package com.zlstudy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlstudy.dao.UserDao;
import com.zlstudy.dao.hibernate.Page;
import com.zlstudy.entity.User;
import com.zlstudy.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Page<User> findPage(Page<User> page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select u.userId, s.name as schoolName ");
		sb.append("from User u, School s ");
		sb.append("where u.schoolId = s.id and u.userId = :userId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", 13323712806L);
		return userDao.findPage(User.class, page, sb.toString(), params);
	}
	
	public Page findPageNullClasz(Page page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select u.userId, s.name as schoolName ");
		sb.append("from User u, School s ");
		sb.append("where u.schoolId = s.id and u.userId = :userId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", 13323712806L);
		return userDao.findPage(null, page, sb.toString(), params);
	}

	public List<User> find() {
		StringBuffer hql = new StringBuffer();
//		hql.append("select u.userId, s.name as schoolName ");/报错，u.userId必须有别名                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		hql.append("select u.userId as userId, s.name as schoolName ");
		hql.append("from User u, School s ");
		hql.append("where u.schoolId = s.id and u.userId = 13403701488");
		Map<String, Object> params = null;
		return userDao.findByHql(User.class, hql.toString(), params);
	}
	
	public List<User> find(Long userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.userId as userId, s.name as schoolName ");
		sql.append("from user u, school s ");
		sql.append("where u.schoolId = s.id and u.userId = ?");
		Map<String, Type> scalar = new HashMap<String, Type>();
		scalar.put("userId", StandardBasicTypes.LONG);
		return userDao.find(User.class, scalar, sql.toString(), userId);
//		return userDao.find(User.class, sql.toString(), userId);//报错
	}
	
	public void getJdbc() {
		userDao.getJdbc();
	}

	public void saveBatch(List<User> users) {
		userDao.saveBatch(users);
	}
}
