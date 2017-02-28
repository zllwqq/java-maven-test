package com.zlstudy.dao;

import org.springframework.stereotype.Repository;

import com.zlstudy.dao.hibernate.SimpleHibernateDao;
import com.zlstudy.entity.School;

@Repository
public class SchoolDao extends SimpleHibernateDao<School, Integer> {
	
}
