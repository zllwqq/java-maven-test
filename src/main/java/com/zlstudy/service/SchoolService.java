package com.zlstudy.service;

import java.util.List;

import com.zlstudy.entity.School;

public interface SchoolService {
	
	/**
	 * 根据id获取school
	 * @param id
	 * @return
	 */
	public School get(Integer id);
	
	public School load(Integer id);
	
	public List<School> getAll();
	
	public void delete(Integer id);
	
	public void save(School school);
	
	public void saveBatch(List<School> schools);
	
	public void update(School school);
	
}
