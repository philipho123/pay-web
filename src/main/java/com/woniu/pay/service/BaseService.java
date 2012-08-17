package com.woniu.pay.service;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.woniu.pay.base.BaseHibernateHqlDao;
import com.woniu.pay.base.BaseHibernateSqlDao;
import com.woniu.pay.base.HibernateSpExecutableDao;
import com.woniu.pay.base.Pojo;
import com.woniu.pay.util.BeanUtils;

/**
 * 经过简单包装的Service（以后会慢慢扩展）
 * 
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Transactional
public class BaseService<T extends Pojo, PK extends Serializable> {

	protected Logger logger = Logger.getLogger(this.getClass());

	protected BaseHibernateHqlDao<T, PK> dao;

	protected HibernateSpExecutableDao HSPDao;

	protected BaseHibernateSqlDao HSDAO;
	private Class<T> clazz;

	protected Order[] orderBy() {
		return null;
	}

	protected Criterion[] makeCS(Map<String, Object> jsonMap) {
		return new Criterion[0];
	}

	protected Object[] makeHQL(Map<String, Object> jsonMap) {
		return new Object[0];
	}

	@SuppressWarnings("unchecked")
	public BaseService() {
		this.clazz = BeanUtils.getSuperClassGenricType(getClass());
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		dao = new BaseHibernateHqlDao<T, PK>(sessionFactory, clazz);
		HSPDao = new HibernateSpExecutableDao(sessionFactory);
		HSDAO = new BaseHibernateSqlDao(sessionFactory);
	}

	@Transactional(readOnly = true)
	public T get(PK id) throws Exception {
		return dao.get(id);
	}

	public void save(T entity) throws Exception {
		dao.save(entity);
	}

	public void save(Collection<T> entities) throws Exception {
		dao.save(entities);
	}

	public void delete(PK id) throws Exception {
		dao.delete(id);
	}

	public void delete(Collection<T> entities) throws Exception {
		dao.delete(entities);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() throws Exception {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public T findUnique(String propertyName, Object value) {
		return dao.findUniqueByProperty(propertyName, value);
	}

	@Transactional(readOnly = true)
	public List<T> findByProperty(String propertyName, Object value) {
		return dao.findByProperty(propertyName, value);
	}

	public String getIdName(Class clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = dao.getSessionFactory().getClassMetadata(clazz);
		Assert.notNull(meta, "Class " + clazz + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName() + " has no identifier property define.");
		return idName;
	}

	public List<T> find(Map<String, Object> jsonMap, Integer start, Integer limit) {
		return dao.findByCriteria(start, limit, orderBy(), makeCS(jsonMap));
	}

	public Integer findCount(Map<String, Object> jsonMap) {
		return dao.countQueryResult(makeCS(jsonMap));
	}

	public List findByQuery(String hql, Integer start, Integer limit, Object... values) {
		return dao.find(hql, start, limit, values);
	}

	public Integer findCountByQuery(String hql, Object... values) {
		return dao.countQueryResult(hql, values);
	}

	public boolean isUniqueProperty(String propertyName, Object newValue, Object orgValue) {
		return dao.isPropertyUnique(propertyName, newValue, orgValue);
	}

	public Session getSession() {
		return dao.getSession();
	}

	public Connection getConnection() {
		return getSession().connection();
	}

	protected boolean isNotNullObj(Object obj) {
		// && !"null".equalsIgnoreCase(String.valueOf(obj))
		return obj != null && StringUtils.isNotBlank(String.valueOf(obj));
	}

	protected boolean isNullObj(Object obj) {
		return !isNotNullObj(obj);
	}

	public Integer findCountBySQL(String sql, Object... values) {
		return dao.countQueryResult(sql, values);
	}

	public SQLQuery createSQLQuery(String sql, Object... values) {
		Assert.hasText(sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据SQL语句查询结果集(List<Map<String,Object>>)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param values
	 *            查询条件
	 * @return
	 * @throws DataAccessException
	 */
	public List findBySQL(String sql, Object... values) throws DataAccessException {
		return findBySQL(sql, true, values);
	}

	/**
	 * 根据SQL语句查询结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param array2map
	 *            是否将记录转换为map
	 * @param values
	 *            查询条件
	 * @return
	 * @throws DataAccessException
	 */
	public List findBySQL(String sql, boolean array2map, Object... values) throws DataAccessException {
		return findBySQL(sql, null, null, array2map, values);
	}

	/**
	 * 根据SQL语句查询分页结果集(List<Map<String,Object>>)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param start
	 *            记录起始位置
	 * @param limit
	 *            每页记录数
	 * @param values
	 *            查询条件
	 * @return
	 * @throws DataAccessException
	 */
	public List findBySQL(String sql, Integer start, Integer limit, Object... values) throws DataAccessException {
		return findBySQL(sql, start, limit, true, values);
	}

	/**
	 * 根据SQL语句查询分页结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param start
	 *            记录起始位置
	 * @param limit
	 *            每页记录数
	 * @param array2map
	 *            是否将记录转换为map
	 * @param values
	 *            查询条件
	 * @return
	 * @throws DataAccessException
	 */
	public List findBySQL(String sql, Integer start, Integer limit, boolean array2map, Object... values)
			throws DataAccessException {
		SQLQuery query = createSQLQuery(sql, values);
		if (array2map) {
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		}
		if (start != null) {
			query.setFirstResult(start);
		}
		if (limit != null) {
			query.setMaxResults(limit);
		}
		return query.list();
	}

}
