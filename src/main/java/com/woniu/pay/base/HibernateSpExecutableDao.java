package com.woniu.pay.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;

import com.woniu.pay.support.JdbcHelper;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HibernateSpExecutableDao extends BaseHibernateDao implements BaseSpDao {

	public HibernateSpExecutableDao() {
		super();
	}

	public HibernateSpExecutableDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 生成{call spName (?,?,?)}格式的存储过程SQL语句
	 * 
	 * @param spName
	 *            存储过程名
	 * @param paramsSize
	 *            存储过程参数个数，包括输入参数和输出参数
	 * @return String
	 */
	protected String genSpSql(String spName, int paramsSize) {
		Assert.hasText(spName);
		StringBuffer buffer = new StringBuffer();
		buffer.append("{call ").append(spName).append(" (");
		if (paramsSize > 0) {
			for (int i = 0; i < paramsSize; i++) {
				buffer.append("?");
				if (i != paramsSize - 1) {
					buffer.append(",");
				}
			}
		}
		buffer.append(")}");
		if (logger.isDebugEnabled())
			logger.debug("存储过程==>>: {}" + buffer);

		return buffer.toString();
	}

	/**
	 * 执行无参数和返回值的存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @throws DataAccessException
	 */
	public void execute(String spName) throws DataAccessException {
		String sql = genSpSql(spName, 0);
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);
			cstmt.execute();
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}

	}

	/**
	 * 执行无返回值的存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param inParams
	 *            可变输入参数
	 * @throws DataAccessException
	 */
	public void execute(String spName, Map<String, Object> inParams) throws DataAccessException {
		String sql = genSpSql(spName, inParams.size());
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);

			if (inParams != null) {
				Set<Map.Entry<String, Object>> entries = inParams.entrySet();
				for (Map.Entry<String, Object> entry : entries) {
					String key = entry.getKey();
					Object value = entry.getValue();
					cstmt.setObject(key, value, JdbcHelper.translateType(value));
				}
			}
			cstmt.execute();
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	/**
	 * 执行无返回值的存储过程 按照输入参数的顺序传入数据值
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param inValues
	 *            存储过程输入参数。
	 */
	public void execute(String spName, Object... inValues) throws DataAccessException {
		String sql = genSpSql(spName, inValues.length);
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);

			if (inValues != null) {
				for (int i = 0; i < inValues.length; i++) {
					cstmt.setObject(i, inValues[i], JdbcHelper.translateType(inValues[i]));
				}
			}
			cstmt.execute();
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	/**
	 * 执行无输入参数的存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param outValues
	 *            输出参数【存储过程输出参数名】。
	 * @return 存储过程返回值。
	 */
	public Map<String, Object> executeWithResult(String spName, String... outValues) throws DataAccessException {
		String sql = genSpSql(spName, outValues.length);
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);
			ParameterMetaData pmd = cstmt.getParameterMetaData();
			for (int i = 1, count = pmd.getParameterCount(); i <= count; i++) {
				cstmt.registerOutParameter(i, pmd.getParameterType(i));
			}
			cstmt.execute();
			Map<String, Object> result = new HashMap<String, Object>();
			if (outValues != null) {
				for (int i = 0; i < outValues.length; i++) {
					result.put(outValues[i], cstmt.getObject(outValues[i]));
				}
			}
			return result;

		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	/**
	 * 执行存储过程。 按照输入参数的顺序传入数据值 存储过程的in参数和inout参数的必须先定义
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param inValues
	 *            输出参数
	 * @param outValues输出参数
	 *            【存储过程输出参数名】。
	 * @return 存储过程返回值。
	 */
	public Map<String, Object> executeWithResult(String spName, Object[] inValues, String... outValues)
			throws DataAccessException {
		String sql = genSpSql(spName, inValues.length + outValues.length);
		try {
			// Connection conn = this.getConnection();
			CallableStatement cstmt = this.getConnection().prepareCall(sql);
			ParameterMetaData pmd = cstmt.getParameterMetaData();
			for (int i = 1, count = pmd.getParameterCount(); i <= count; i++) {
				int mode = pmd.getParameterMode(i);
				int type = pmd.getParameterType(i);
				if (mode == ParameterMetaData.parameterModeOut || mode == ParameterMetaData.parameterModeInOut) {
					cstmt.registerOutParameter(i, type);
				}
				// 输入参数传值
				if (i <= inValues.length) {
					cstmt.setObject(i, inValues[i - 1], JdbcHelper.translateType(inValues[i - 1]));
				}
			}

			cstmt.execute();
			Map<String, Object> result = new HashMap<String, Object>();
			if (outValues != null) {
				for (int i = 0; i < outValues.length; i++) {
					try {
						result.put(outValues[i], cstmt.getObject(outValues[i]));
					} catch (Exception e) {
						logger.info("---------------注册的回传,值不存在,用索引获取中-----------------");
						result.put(outValues[i], cstmt.getObject(inValues.length + i + 1));
					} finally {
						logger.info("---------------执行存储过程结束-----------------");
					}
				}
			}

			return result;

		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	/**
	 * 执行存储过程。 按照输入参数的顺序传入数据值
	 * 存储过程的in参数和inout参数的必须先定义{1,"YES","2011-12-25","piResult"}
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param outAmount
	 *            输出参数的个数
	 * @param values输出参数
	 *            【输入参数值和输出参数名】。
	 * @return 存储过程返回值。
	 */
	public Map<String, Object> executeWithResult(String spName, int outAmount, Object... values)
			throws DataAccessException {
		String sql = genSpSql(spName, values.length);
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);
			ParameterMetaData pmd = cstmt.getParameterMetaData();
			for (int i = 1, count = pmd.getParameterCount(); i <= count; i++) {

				int mode = pmd.getParameterMode(i);
				int type = pmd.getParameterType(i);

				if (mode == ParameterMetaData.parameterModeOut || mode == ParameterMetaData.parameterModeInOut) {
					cstmt.registerOutParameter(i, type);
				}

				if (mode == ParameterMetaData.parameterModeIn || mode == ParameterMetaData.parameterModeInOut) {
					if (i <= values.length) {
						cstmt.setObject(i, values[i - 1], JdbcHelper.translateType(values[i - 1]));
					}
				}
			}

			cstmt.execute();
			Map<String, Object> result = new HashMap<String, Object>();
			for (int i = outAmount; i > 0; i--) {
				result.put(values[values.length - i].toString(), cstmt.getObject(values.length - i + 1));
			}
			return result;

		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	/**
	 * 执行无参数输入的存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param outParams
	 *            可变输出参数
	 * @throws DataAccessException
	 * @return Map
	 */
	public Map<String, Object> executeWithResult(String spName, Map<String, Object> outParams)
			throws DataAccessException {
		String sql = genSpSql(spName, outParams.size());
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);
			ParameterMetaData pmd = cstmt.getParameterMetaData();
			for (int i = 1, count = pmd.getParameterCount(); i <= count; i++) {
				int mode = pmd.getParameterMode(i);
				int type = pmd.getParameterType(i);
				if (mode == ParameterMetaData.parameterModeOut || mode == ParameterMetaData.parameterModeInOut) {
					cstmt.registerOutParameter(i, type);
				}
			}
			cstmt.execute();
			Map<String, Object> result = new HashMap<String, Object>();
			Set<Map.Entry<String, Object>> outKeys = outParams.entrySet();
			for (Map.Entry<String, Object> outKey : outKeys) {
				result.put(outKey.getKey(), cstmt.getObject(outKey.getKey()));
			}
			return result;

		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	/**
	 * 执行存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param inParams
	 *            可变输入参数
	 * @param outParams
	 *            可变输出参数
	 * @throws DataAccessException
	 * @return Map
	 */
	public Map<String, Object> executeWithResult(String spName, Map<String, Object> inParams,
			Map<String, Object> outParams) throws DataAccessException {
		String sql = genSpSql(spName, inParams.size() + outParams.size());
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);
			ParameterMetaData pmd = cstmt.getParameterMetaData();
			for (int i = 1, count = pmd.getParameterCount(); i <= count; i++) {
				int mode = pmd.getParameterMode(i);
				int type = pmd.getParameterType(i);
				if (mode == ParameterMetaData.parameterModeOut || mode == ParameterMetaData.parameterModeInOut) {
					cstmt.registerOutParameter(i, type);
				}
			}

			if (inParams != null) {
				Set<Map.Entry<String, Object>> entries = inParams.entrySet();
				for (Map.Entry<String, Object> entry : entries) {
					String key = entry.getKey();
					Object value = entry.getValue();
					cstmt.setObject(key, value, JdbcHelper.translateType(value));
				}
			}
			cstmt.execute();
			Map<String, Object> result = new HashMap<String, Object>();
			Set<Map.Entry<String, Object>> outKeys = outParams.entrySet();
			for (Map.Entry<String, Object> outKey : outKeys) {
				result.put(outKey.getKey(), cstmt.getObject(outKey.getKey()));
			}
			return result;
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}

	}

	/**
	 * 执行指定游标名称的存储过程。
	 * 
	 * @param spName
	 * @param inParams
	 * @param inParams
	 * @param cusorName
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> executeWithResult(String spName, Map<String, Object> inParams,
			Map<String, Object> outParams, String cusorName) throws DataAccessException {
		return executeWithResult(spName, inParams, outParams);
	}

	/**
	 * 执行指定Connecttion对象的存储过程。
	 * 
	 * @param spName
	 * @param inParams
	 * @param inParams
	 * @param cusorName
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> executeWithResult(Connection conn, String spName, Map<String, Object> inParams,
			Map<String, Object> outParams, String cusorName) throws DataAccessException {

		String sql = genSpSql(spName, inParams.size() + outParams.size());
		try {
			if (conn == null)
				return null;

			CallableStatement cstmt = conn.prepareCall(sql);
			ParameterMetaData pmd = cstmt.getParameterMetaData();
			for (int i = 1, count = pmd.getParameterCount(); i <= count; i++) {
				int mode = pmd.getParameterMode(i);
				int type = pmd.getParameterType(i);
				if (mode == ParameterMetaData.parameterModeOut || mode == ParameterMetaData.parameterModeInOut) {
					cstmt.registerOutParameter(i, type);
				}
			}

			if (inParams != null) {
				Set<Map.Entry<String, Object>> entries = inParams.entrySet();
				for (Map.Entry<String, Object> entry : entries) {
					String key = entry.getKey();
					Object value = entry.getValue();
					cstmt.setObject(key, value, JdbcHelper.translateType(value));
				}
			}
			cstmt.execute();
			Map<String, Object> result = new HashMap<String, Object>();
			Set<Map.Entry<String, Object>> outKeys = outParams.entrySet();
			for (Map.Entry<String, Object> outKey : outKeys) {
				result.put(outKey.getKey(), cstmt.getObject(outKey.getKey()));
			}

		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
		return null;
	}

	/**
	 * TODO 执行存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @return
	 * @throws DataAccessException
	 */
	public List executeWithResultset(String spName) throws DataAccessException {
		String sql = genSpSql(spName, 0);
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);
			List resultList = new ArrayList<ArrayList<Object>>();
			ResultSet rs = cstmt.executeQuery();
			while (rs.next()) {
				List<Object> list = new ArrayList<Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					list.add(rs.getObject(i));
				}
				resultList.add(list);
			}
			return resultList;
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	/**
	 * TODO 执行带输入参数的存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @param inParams
	 * @return
	 * @throws DataAccessException
	 */
	public List executeWithResultset(String spName, Map<String, Object> inParams) throws DataAccessException {
		String sql = genSpSql(spName, inParams.size());
		try {
			CallableStatement cstmt = this.getConnection().prepareCall(sql);

			if (inParams != null) {
				Set<Map.Entry<String, Object>> entries = inParams.entrySet();
				for (Map.Entry<String, Object> entry : entries) {
					String key = entry.getKey();
					Object value = entry.getValue();
					cstmt.setObject(key, value, JdbcHelper.translateType(value));
				}
			}
			List resultList = new ArrayList<ArrayList<Object>>();
			ResultSet rs = cstmt.executeQuery();
			while (rs.next()) {
				List<Object> list = new ArrayList<Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					list.add(rs.getObject(i));
				}
				resultList.add(list);
			}

			return resultList;
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	/**
	 * TODO 执行带输入参数的存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @param inValues
	 * @return
	 * @throws DataAccessException
	 */
	public List executeWithResultset(String spName, Object... inValues) throws DataAccessException {
		String sql = genSpSql(spName, inValues.length);
		try {

			CallableStatement cstmt = this.getConnection().prepareCall(sql);

			if (inValues != null) {
				for (int i = 0; i < inValues.length; i++) {
					cstmt.setObject(i, inValues[i], JdbcHelper.translateType(inValues[i]));
				}
			}
			List resultList = new ArrayList<ArrayList<Object>>();
			ResultSet rs = cstmt.executeQuery();
			while (rs.next()) {
				List<Object> list = new ArrayList<Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					list.add(rs.getObject(i));
				}
				resultList.add(list);
			}

			return resultList;
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	public void commit() throws DataAccessException {
		try {
			this.getConnection().commit();
		} catch (SQLException e) {
			throw new DataAccessResourceFailureException("事物提交异常失败", e);
		}

	}

	public void transBegin() throws DataAccessException {

	}

}
