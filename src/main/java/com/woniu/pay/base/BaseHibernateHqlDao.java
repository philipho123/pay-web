/**
 *BaseHibernateCurdDao.java
 *海外计费系统
 *苏州蜗牛数字科技股份有限公司
 */
package com.woniu.pay.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

/**
 * @author Jahn
 * @since 2012-3-26
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class BaseHibernateHqlDao<T, PK extends Serializable> extends
		BaseHibernateDao {
	
	protected Class<T> entityClass;

	public BaseHibernateHqlDao() {
	}

	public BaseHibernateHqlDao(SessionFactory sessionFactory,
			Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/**
	 * 按主键获取持久化对象.
	 * @param id
	 *            主键
	 * @return 持久化对象
	 */
	public T get(final PK id) throws DataAccessException {
		return (T) getSession().get(entityClass, id);
	}
	
	/**
	 * 查询所有持久化对象.
	 * @return list<持久化对象>
	 */
	public List<T> findAll() throws DataAccessException {
		return createCriteria().list();
	}
	
	/**
	 * 创建 或者更新持久化对象.
	 */
	public void save(T entity) throws DataAccessException {
		Assert.notNull(entity);
		getSession().saveOrUpdate(entity);
		if (logger.isDebugEnabled())
			logger.debug("save entity: {}"+ entity);
	}
	
	/**
	 * 批量创建 或者更新持久化对象.
	 * @param Collection<持久化对象>
	 */
	public void save(Collection<T> entities) throws DataAccessException {
		Assert.notNull(entities);
		for (T entity : entities) {
			save(entity);
		}
	}
	/**
	 * 【谨慎使用，不建议删除数据】
	 * 删除持久化对象.
	 * @param 持久化对象
	 */
	public void delete(T entity) throws DataAccessException {
		Assert.notNull(entity);
		getSession().delete(entity);
		getSession().flush();
		if (logger.isDebugEnabled())
			logger.debug("save entity: {}"+ entity);
	}
	
	/**
	 * 【谨慎使用，不建议删除数据】
	 * 通过主键删除持久化对象.
	 * @param id
	 */
	public void delete(PK id) throws DataAccessException {
		Assert.notNull(id);
		delete(get(id));
	}
	
	/**
	 * 【谨慎使用，不建议删除数据】
	 * 批量删除持久化对象.
	 * @param Collection<持久化对象>
	 */
	public void delete(Collection<T> entities) throws DataAccessException {
		Assert.notNull(entities);
		for (T entity : entities) {
			delete(entity);
		}
	}
	/**
	 * 通过HQL语句以及参数值，生成org.hibernate.Query.
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query queryObject = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	public Criteria createCriteria(Criterion... criterions) {
		return createCriteria(null, criterions);
	}

	public Criteria createCriteria(Order[] orders, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		if (orders != null) {
			for (Order o : orders) {
				criteria.addOrder(o);
			}
		}
		return criteria;
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 */
	@SuppressWarnings("rawtypes")
	public List find(String hql, Object... values) throws DataAccessException {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象分页列表
	 * 
	 * @param hql
	 *            hql语句
	 * @param start
	 *            起始记录
	 * @param limit
	 *            页面大小
	 * @param values
	 *            数量可变的参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List find(String hql, Integer start, Integer limit, Object... values)
			throws DataAccessException {
		Query query = createQuery(hql, values);
		if (start != null) {
			query.setFirstResult(start);
		}
		if (limit != null) {
			query.setMaxResults(limit);
		}
		return query.list();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 * @return
	 */
	public Object findUnique(String hql, Object... values)
			throws DataAccessException {
		return createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询Intger类形结果.
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 * @return
	 */
	public Integer findInt(String hql, Object... values)
			throws DataAccessException {
		return (Integer) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Long类形结果.
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 * @return
	 */
	public Long findLong(String hql, Object... values)
			throws DataAccessException {
		return (Long) findUnique(hql, values);
	}

	/**
	 * 按Criterion查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 * @return
	 */
	public List<T> findByCriteria(Criterion... criterions)
			throws DataAccessException {
		return createCriteria(criterions).list();
	}

	public List<T> findByCriteria(Order[] orders, Criterion... criterions)
			throws DataAccessException {
		return createCriteria(orders, criterions).list();
	}

	public List<T> findByCriteria(Integer start, Integer limit, Order[] orders,
			Criterion... criterions) throws DataAccessException {
		Criteria criteria = createCriteria(orders, criterions);
		if (start != null) {
			criteria.setFirstResult(start);
		}
		if (limit != null) {
			criteria.setMaxResults(limit);
		}
		return criteria.list();
	}

	/**
	 * 按Criterion查询对象列表.
	 * 
	 * @param start
	 *            起始记录
	 * @param limit
	 *            页面大小
	 * @param criterions
	 *            数量可变的Criterion.
	 * @return
	 */
	public List<T> findByCriteria(Integer start, Integer limit,
			Criterion... criterions) throws DataAccessException {
		return findByCriteria(start, limit, null, criterions);
	}

	public List<T> findByCriteria(Integer start, Integer limit,
			Criteria criteria) throws DataAccessException {
		if (start != null) {
			criteria.setFirstResult(start);
		}
		if (limit != null) {
			criteria.setMaxResults(limit);
		}
		return criteria.list();
	}

	/**
	 * 按属性查找对象列表.
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value)
			throws DataAccessException {
		Assert.hasText(propertyName);
		return createCriteria(Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 按属性查找唯一对象.
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 */
	public T findUniqueByProperty(String propertyName, Object value)
			throws DataAccessException {
		Assert.hasText(propertyName);
		return (T) createCriteria(Restrictions.eq(propertyName, value))
				.uniqueResult();
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一. 在修改对象的情景下,如果属性新修改的值(newValue)等于属性原值(orgValue)则不作比较.
	 * 
	 * @param propertyName
	 *            属性值
	 * @param newValue
	 *            新修改的值
	 * @param orgValue
	 *            原值
	 * @return
	 */
	public boolean isPropertyUnique(String propertyName, Object newValue,
			Object orgValue) throws DataAccessException {
		if (newValue == null || newValue.equals(orgValue))
			return true;

		Object object = findUniqueByProperty(propertyName, newValue);
		return (object == null);
	}

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @param c
	 *            Criteria条件
	 * @return
	 */
	public Integer countQueryResult(Criteria c) throws DataAccessException {
		CriteriaImpl impl = (CriteriaImpl) c;
		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		@SuppressWarnings("unused")
		List<CriteriaImpl.OrderEntry> orderEntries = null;
		// try {
		// orderEntries = (List) BeanUtils.forceGetProperty(impl,
		// "orderEntries");
		// BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
		// } catch (Exception e) {
		// logger.error("不可能抛出的异常:{}", e.getMessage());
		// }
		// 执行Count查询
		Object count = c.setProjection(Projections.rowCount()).uniqueResult();
		if (count == null) {
			return null;
		}
		Integer totalCount = (Integer) count;

		// 将之前的Projection和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}

		// try {
		// BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
		// } catch (Exception e) {
		// logger.error("不可能抛出的异常:{}", e.getMessage());
		// }

		return totalCount;
	}

	public Integer countQueryResult(Criterion... criterions)
			throws DataAccessException {
		return countQueryResult(createCriteria(criterions));
	}

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param values
	 *            数量可变的参数
	 * @return
	 */
	public Integer countQueryResult(String queryString, Object... values)
			throws DataAccessException {
		String countQueryString = "select count(*) "
				+ removeSelect(removeOrders(queryString));
		countQueryString = countQueryString.replaceAll(" join\\s fetch ",
				" join ");
		Object count = null;
		try {
			count = createQuery(countQueryString, values).uniqueResult();
		} catch (Exception e) {
			logger.error("不可能抛出的异常：{}"+ e.getMessage());
		}
		return count == null ? null : (Integer.parseInt(count.toString()));
	}

	/**
	 * 删除select语句,慎用
	 * 
	 * @param queryString
	 * @return
	 */
	private String removeSelect(String queryString) {
		Assert.hasText(queryString);
		int fromIdx = queryString.toLowerCase().indexOf(" from ");
		Assert.isTrue(fromIdx != -1, "queryString [" + queryString
				+ "] must has a keyword 'from'.");
		return queryString.substring(fromIdx);
	}

	/**
	 * 删除order by语句,慎用
	 * 
	 * @param queryString
	 * @return
	 */
	private static String removeOrders(String queryString) {
		Assert.hasText(queryString);
		Pattern p = Pattern.compile(" order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(queryString);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

}
