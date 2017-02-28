package com.zlstudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlstudy.dao.SchoolDao;
import com.zlstudy.entity.School;
import com.zlstudy.service.SchoolService;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

	private SchoolDao schoolDao;
	
	public SchoolDao getSchoolDao() {
		return schoolDao;
	}

	@Autowired
	public void setSchoolDao(SchoolDao schoolDao) {
		this.schoolDao = schoolDao;
	}

	public School get(Integer id) {
		System.out.println("测试一级缓存session");
		School school1 = schoolDao.get(id);//会打印sql语句
		School school2 = schoolDao.get(id);//不会打印sql语句
		return schoolDao.get(id);
	}
	
	public School load(Integer id) {
		return schoolDao.load(id);
	}

	public List<School> getAll() {
//		String hql = "from School";
//		Map params = null;
//		return schoolDao.find(hql, params);
		System.out.println("测试");
		//速度最优
		String sql = "select * from school";
		return schoolDao.find(School.class, sql);
		
//		return schoolDao.getAll();
	}
	
	public void delete(Integer id) {
		schoolDao.delete(id);
	}
	
	public void save(School school) {
		schoolDao.save(school);
	}
	
	public void update(School school) {
		schoolDao.save(school);
	}

	public void saveBatch(List<School> schools) {
		schoolDao.saveBatch(schools);
	}
}
