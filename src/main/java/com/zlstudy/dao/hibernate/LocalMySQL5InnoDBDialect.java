package com.zlstudy.dao.hibernate;

import java.sql.Types;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.type.StandardBasicTypes;

public class LocalMySQL5InnoDBDialect extends MySQL5InnoDBDialect {
	
	public LocalMySQL5InnoDBDialect() {
		super();
		registerHibernateType( Types.BIGINT, StandardBasicTypes.LONG.getName() );
	}
}
