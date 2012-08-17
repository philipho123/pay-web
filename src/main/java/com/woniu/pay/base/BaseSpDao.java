package com.woniu.pay.base;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 可以执行存储过程的DAO接口。
 * 
 * @version 1.0
 * 
 */
@SuppressWarnings("rawtypes")
public interface BaseSpDao extends BaseDao {

	/**
	 * 执行无参数和返回值的存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @throws DataAccessException
	 */
	void execute(String spName) throws DataAccessException;

	/**
	 * 执行无返回值的存储过程
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param inParames
	 *            存储过程参数。
	 */
	void execute(String spName, Map<String, Object> inParams)
			throws DataAccessException;

	/**
	 * 执行无返回值的存储过程
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param inValues
	 *            存储过程参数。
	 */
	void execute(String spName, Object... inValues) throws DataAccessException;



	/**
	 * 执行无输入参数存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param outParams
	 *            存储过程输出参数名
	 * @return 存储过程返回值。
	 */
	Map<String, Object> executeWithResult(String spName,
			Map<String, Object> outParams) throws DataAccessException;

	/**
	 * 执行无输入参数存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param outValues
	 *            输出参数【存储过程输出参数名】。
	 * @return 存储过程返回值。
	 */
	Map<String, Object> executeWithResult(String spName, String... outValues)
			throws DataAccessException;

	/**
	 * 执行存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param inValues
	 *            输出参数
	 * @param outValues输出参数
	 *            【存储过程输出参数名】。
	 * @return 存储过程返回值。
	 */
	Map<String, Object> executeWithResult(String spName, Object []inValues,
			String... outValues) throws DataAccessException;

	/**
	 * 执行存储过程。
	 * 
	 * @param spName
	 *            存储过程名。
	 * @param outAmount
	 *            输出参数的个数
	 * @param values输出参数
	 *            【存储过程输出参数名】。
	 * @return 存储过程返回值。
	 */
	Map<String, Object> executeWithResult(String spName, int outAmount,
			Object... values) throws DataAccessException;
	/**
	 * 
	 * @param spName
	 * @param inParams
	 * @param outParams
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> executeWithResult(String spName,
			Map<String, Object> inParams, Map<String, Object> outParams)
			throws DataAccessException;

	/**
	 * 
	 * @param spName
	 * @param inParams
	 * @param inParams
	 * @param cusorName
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> executeWithResult(String spName,
			Map<String, Object> inParams, Map<String, Object> outParams,
			String cusorName) throws DataAccessException;

	/**
	 * @param conn
	 * @param spName
	 * @param inParams
	 * @param outParams
	 * @param cusorName
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> executeWithResult(Connection conn, String spName,
			Map<String, Object> inParams, Map<String, Object> outParams,
			String cusorName) throws DataAccessException;

	/**
	 * 执行无输出参数的存储过程记录。
	 * 
	 * @param spName
	 * @param values
	 *            []
	 * @return
	 * @throws DataAccessException
	 */
	List executeWithResultset(String spName, Object... values)
			throws DataAccessException;

	/**
	 * TODO 执行存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @return
	 * @throws DataAccessException
	 */
	List executeWithResultset(String spName) throws DataAccessException;

	/**
	 * TODO 执行带输入参数的存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @param inParams
	 * @return
	 * @throws DataAccessException
	 */
	List executeWithResultset(String spName, Map<String, Object> inParams)
			throws DataAccessException;

	/**
	 * 编程式事务提交
	 * 
	 * @throws DataAccessException
	 */
	void commit() throws DataAccessException;

	/**
	 * 编程式事务开启
	 * 
	 * @throws DataAccessException
	 */
	void transBegin() throws DataAccessException;
}

