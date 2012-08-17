/**
 *BaseHibernateDAO.java
 *海外计费系统
 *苏州蜗牛数字科技股份有限公司
 */
package com.woniu.pay.base;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Jahn
 * @since 2012-3-26
 * @version 1.0
 */
public abstract class BaseHibernateDao implements BaseDao {

	protected Logger logger = Logger.getLogger(this.getClass());

	protected SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Connection getConnection() {
		return getSession().connection();
	}
}
