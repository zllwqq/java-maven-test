package com.zlstudy.dao.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.hibernate.type.NullableType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.zlstudy.utils.ReflectionUtils;

/**
 * 参考springside框架
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释.
 * 
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author 
 */
@Repository
public class SimpleHibernateDao<T, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public SimpleHibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/**
	 * 取得sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候在子类重载本函数.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 取得当前Session.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存新增或修改的对象.
	 */
	public void save(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: {}", entity);
	}

	/**
	 * 删除对象.
	 * 
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	/**
	 * 按id删除对象.
	 */
	public void delete(final PK id) {
		Assert.notNull(id, "id不能为空");
		delete(load(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/**
	 * 按id获取对象.延迟加载
	 */
	public T load(final PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * 按id获取对象.
	 */
	public T get(final PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().get(entityClass, id);
	}
	
	/**
	 * 批量保存
	 * @param list
	 */
	public void saveBatch(List<T> list) {
		Assert.notNull(list, "list不能为空");
		Session session = getSession();
		for (int i=0; i<list.size(); i++) {
			session.save(list.get(i));
			if (i%50 == 0) {
				session.flush();
				session.clear();
			}
		}
	}
	
	/**
	 * 按id列表获取对象列表.
	 */
	public List<T> get(final Collection<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/**
	 *	获取全部对象.
	 */
	public List<T> getAll() {
		return find();
	}

	/**
	 *	获取全部对象, 支持按属性行序.
	 */
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/**
	 * 按属性查找对象列表, 匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	//hql
	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public <X> List<X> findByHql(Class<X> clasz, final String hql, final Object... values) {
		return createQuery(hql, values).setResultTransformer(Transformers.aliasToBean(clasz)).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public <X> List<X> findByHql(Class<X> clasz, final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).setResultTransformer(Transformers.aliasToBean(clasz)).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}
	
	public <X> X findUnique(Class<X> clasz, final String sql, final Object... values) {
		Query q = createSQLQuery(sql, values);
		if (null != clasz) {
			if ("Map".equals(clasz.getSimpleName())) {
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			} else {
				q.setResultTransformer(Transformers.aliasToBean(clasz));
			}
		}
		return (X)  q.uniqueResult();
	}
	
	public <X> X findUnique(Class<X> clasz, final Map<String, Type> scalar, final String sql, final Object... values) {
		SQLQuery q = createSQLQuery(sql, values);
		if (null != clasz) {
			if ("Map".equals(clasz.getSimpleName())) {
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			} else {
				q.setResultTransformer(Transformers.aliasToBean(clasz));
			}
		}
		if (null != scalar) {
			for (String key : scalar.keySet()) {
	            q.addScalar(key, scalar.get(key));
            }
		}
		return (X)  q.uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	//测试使用
	public void getJdbc() {
		Connection conn = null;
		String sql = "select name from school";
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData data = rs.getMetaData();
//			System.out.println(data.getColumnType(1));
//			System.out.println(data.getColumnTypeName(1));
			String alias = data.getColumnLabel(1);
			String nameField = data.getColumnName(1);
			Object name = rs.getObject(nameField);
			System.out.println(name);
			while (rs.next()) {
//				System.out.println(rs.getObject(1).getClass());
//				System.out.println(rs.getObject("name").getClass());
//				System.out.println(rs.getObject("createTime").getClass());
//				System.out.println(rs.getObject("expireTime").getClass());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
	
	//sql
	/**
	 * 
	 * @param clasz
	 * @param scalar
	 * @param sql
	 * @param values
	 * @return
	 */
	public <X> List<X> find(Class<X> clasz, final Map<String, Type> scalar, final String sql, final Object... values) {
		SQLQuery q = createSQLQuery(sql, values);
		if (null != clasz) {
			if ("Map".equals(clasz.getSimpleName())) {
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			} else {
				q.setResultTransformer(Transformers.aliasToBean(clasz));
			}
		}
		if (null != scalar) {
			for (String key : scalar.keySet()) {
	            q.addScalar(key, scalar.get(key));
            }
		}
		return q.list();
	}
	
	/**
	 * 根据sql查询对象列表
	 * @param clasz 返回类型
	 * @param sql
	 * @param values
	 * @return
	 */
	public <X> List<X> find(Class<X> clasz, final String sql, final Object... values) {
//		Query q = createSQLQuery(sql, values);
//		if (null != clasz) {
//			if ("Map".equals(clasz.getSimpleName())) {
//				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//			} else {
//				q.setResultTransformer(Transformers.aliasToBean(clasz));
//			}
//		}
//		return q.list();
		return this.find(clasz, null, sql, values);
	}
	
	/**
	 * 根据sql查询对象列表
	 * @param clasz 返回类型
	 * @param sql
	 * @param values
	 * @return
	 */
	public <X> List<X> find(Class<X> clasz, final String sql, final Map<String, ?> values) {
		Query q = createSQLQuery(sql, values);
		if (null != clasz) {
			if ("Map".equals(clasz.getSimpleName())) {
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			} else {
				q.setResultTransformer(Transformers.aliasToBean(clasz));
			}
		}
		return q.list();
	}
	
	/**
	 * 执行sql进行更新/删除
	 * @param sql
	 * @param values
	 * @return
	 */
	public int excuteExecute(final String sql, final Object... values) {
		return createSQLQuery(sql, values).executeUpdate();
	}
	
	/**
	 * 执行sql进行更新/删除
	 * @param sql
	 * @param values
	 * @return
	 */
	public int excuteExecute(final String sql, final Map<String, ?> values) {
		return createSQLQuery(sql, values).executeUpdate();
	}
	
	/**
	 * 根据sql与参数列表创建SQLQuery对象
	 * @param sqlQueryString
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public SQLQuery createSQLQuery(final String sqlQueryString, final Object... values) {
		Assert.hasText(sqlQueryString, "queryString不能为空");
		SQLQuery query = getSession().createSQLQuery(sqlQueryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 根据sql与参数列表创建SQLQuery对象
	 * @param sqlQueryString
	 * @param values 命名参数
	 * @return
	 */
	public SQLQuery createSQLQuery(final String sqlQueryString, final Map<String, ?> values) {
		Assert.hasText(sqlQueryString, "queryString不能为空");
		SQLQuery query = getSession().createSQLQuery(sqlQueryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	//Criteria
	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
	 * 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 为Query添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}