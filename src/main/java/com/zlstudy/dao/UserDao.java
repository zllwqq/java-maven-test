package com.zlstudy.dao;

import org.springframework.stereotype.Repository;

import com.zlstudy.dao.hibernate.PagingHinernateDao;
import com.zlstudy.entity.User;

@Repository
public class UserDao extends PagingHinernateDao<User, Long> {

}
