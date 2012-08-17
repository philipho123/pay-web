package com.woniu.pay.merchant.web;


import static com.woniu.pay.constants.HttpSessionConstants.USER_ID_SESSION_KEY;
import static com.woniu.pay.constants.HttpSessionConstants.USER_SESSION_KEY;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.woniu.pay.util.IPUtils;


public abstract class AbstractWebAction {

	private static final Log logger = LogFactory.getLog(AbstractWebAction.class);

	public AbstractWebAction() {
	}

	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	/**
	 * 设置request中Attribute的值
	 * 
	 * @param name
	 * @param obj
	 */
	protected void setRequestAttribute(String name, Object obj) {
		getRequest().setAttribute(name, obj);
	}

	/**
	 * 获取request中Parameter，无则为null
	 * 
	 * @param name
	 * @return
	 */
	protected String getRequestParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * 设置session中Attribute
	 * 
	 * @param name
	 * @param obj
	 */
	protected void setSessionAttribute(String name, Object obj) {
		getRequest().getSession().setAttribute(name, obj);
	}

	/**
	 * 获取session中Attribute，无则为null
	 * 
	 * @param name
	 * @return
	 */
	protected Object getSessionAttribute(String name) {
		return getRequest().getSession().getAttribute(name);
	}

	/**
	 * 记录参数
	 * 
	 */
	@SuppressWarnings("rawtypes")
	protected void logParameters(HttpServletRequest request) {

		StringBuffer sbLog = new StringBuffer();
		/** 获取 IP */
		sbLog.append("clientIP=").append(IPUtils.getClientIPFromProxy(getRequest())).append(" \t");

		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = getRequestParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					sbLog.append("[").append(paramName).append("=").append(paramValue).append("]");
				}
			}
		}
		/** Log */
		logger.info(sbLog.toString());
	}

	/**
	 * 错误信息 返回，用于显示在页面上
	 * 
	 * @param <T>
	 * 
	 * @param request
	 * @param error
	 *            错误信息
	 * @param page
	 *            错误页面
	 * 
	 */
	public String error(HttpServletRequest request, Object error, String page) {
		setRequestAttribute("error", error);
		return page;
	}

	/**
	 * 成功 返回，用于显示在页面上
	 * 
	 * @param request
	 * @param error
	 *            错误信息
	 * @param page
	 *            错误页面
	 * 
	 */
	public String success(HttpServletRequest request, Object success, String page) {
		setRequestAttribute("success", success);
		return page;
	}

	/**
	 * 操作痕迹
	 * 
	 * 用户填写的信息,返回
	 * 
	 */
	protected void setTrace(String[] traces) {
		for (String trace : traces) {
			String value = getRequestParameter(trace);
			if (StringUtils.isNotEmpty(value)) {
				setRequestAttribute(trace, value);
			}
		}
	}

	/**
	 * 删除session中的存值
	 * 
	 * @param key
	 */
	public void removeSessionAttibute(String key) {
		getRequest().getSession().removeAttribute(key);
	}

	@SuppressWarnings("rawtypes")
	public Map getRequestParameterMap() {
		return getRequest().getParameterMap();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getRequestSingleValue() {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String[]> map = getRequest().getParameterMap();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String[] values = (String[]) map.get(key);
			result.put(key, values.length > 0 ? values[0] : null);
		}
		return result;
	}

	public String getUserAccount() {
		Object obj = getSessionAttribute(USER_SESSION_KEY);
		if (obj == null) {
			return null;
		}
		return (String) obj;
	}

	public Long getUserId() {
		Object obj = getSessionAttribute(USER_ID_SESSION_KEY);
		if (obj == null) {
			return null;
		}
		return (Long) obj;
	}
	
	public String[] getRequestParameterValues(String name){
		return getRequest().getParameterValues(name);
	}

}