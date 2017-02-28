package com.zlstudy.service;

import java.util.List;

import com.zlstudy.dao.hibernate.Page;
import com.zlstudy.entity.User;

public interface UserService {
	
	public Page<User> findPage(Page<User> page);
	
	public Page findPageNullClasz(Page page);
	
	public List<User> find();
	
	public void getJdbc();
	
	public void saveBatch(List<User> users);
	
	public List<User> find(Long userId);
}
