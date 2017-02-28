package com.zlstudy.dao;

import org.springframework.stereotype.Repository;

import com.zlstudy.dao.hibernate.SimpleHibernateDao;
import com.zlstudy.entity.LoginLog;

@Repository
public class LoginLogDao extends SimpleHibernateDao<LoginLog, Integer> {
	
}
