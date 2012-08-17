/**
 *BaseHibernateCurdDao.java
 *海外计费系统
 *苏州蜗牛数字科技股份有限公司
 */
package com.woniu.pay.base;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

/**
 * @author Jahn
 * @since 2012-3-26
 * @version 1.0
 */
public class BaseHibernateSqlDao extends
		BaseHibernateDao {
	

	public BaseHibernateSqlDao() {
	}
	
	public BaseHibernateSqlDao(SessionFactory sessionFactory) {
		this.sessionFactory =sessionFactory;
	}


	public Connection getConnection() {
		return getSession().connection();
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
	 * @param sql SQL语句
	 * @param values 查询条件
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public List findBySQL(String sql, Object... values) throws DataAccessException {
		return findBySQL(sql, true, values);
	}

	/**
	 * 根据SQL语句查询结果集
	 * 
	 * @param sql SQL语句
	 * @param array2map 是否将记录转换为map
	 * @param values 查询条件
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public List findBySQL(String sql, boolean array2map, Object... values) throws DataAccessException {
		return findBySQL(sql, null, null, array2map, values);
	}

	/**
	 * 根据SQL语句查询分页结果集(List<Map<String,Object>>)
	 * 
	 * @param sql SQL语句
	 * @param start 记录起始位置
	 * @param limit 每页记录数
	 * @param values 查询条件
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public List findBySQL(String sql, Integer start, Integer limit, Object... values) throws DataAccessException {
		return findBySQL(sql, start, limit, true, values);
	}

	/**
	 * 根据SQL语句查询分页结果集
	 * 
	 * @param sql SQL语句
	 * @param start 记录起始位置
	 * @param limit 每页记录数
	 * @param array2map 是否将记录转换为map
	 * @param values 查询条件
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public List findBySQL(String sql, Integer start, Integer limit, boolean array2map, Object... values) throws DataAccessException {
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

	/**
	 * 根据SQL语句得到记录总数
	 * 
	 * @param sql SQL语句
	 * @param values 查询条件
	 * @return
	 * @throws DataAccessException
	 */
	public Integer findCountBySQL(String sql, Object... values) throws DataAccessException {
		try {
			Object count = createSQLQuery(sql, values).uniqueResult();
		 	return count == null ? null : (Integer) count;
		} catch (Exception e) {
			return null;
		}
	}
}
