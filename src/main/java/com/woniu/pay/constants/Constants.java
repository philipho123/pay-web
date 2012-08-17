package com.woniu.pay.constants;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * 蜗牛海外计费系统常量数据定义
 * @author jahn
 * @since 2012-03-22
 */
public interface Constants {
	/**
	 * 通用状态--禁用、注销、否
	 */
	public static final int COMMON_FALSE_OR_FAILED = 0;
	/**
	 * 通用状态--启用、正常、是
	 */
	public static final int COMMON_TRUE_OR_SUCCESS = 1;

	/**
	 * 通用状态--操作成功
	 */
	public static final String COMMON_OP_SUCCESS = "SUCCESS";
	/**
	 * 通用状态--操作失败
	 */
	public static final String COMMON_OP_FAILED = "FAILED";
	
	/**
	 *日期类型SimpleDateFormat【格式：yyyy-MM-dd HH:mm:ss】
	 */
	public static final SimpleDateFormat SDF_SEC0NDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static SimpleDateFormat SDF_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 *日期类型SimpleDateFormat【格式：yyyy-MM-dd】
	 */
	public static final SimpleDateFormat SDF_DAY = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 *两位小数数字类型格式DecimalFormat【格式：0.00】
	 */
	 public static final DecimalFormat DECIMAL_FORMAT_TWO_DIGIT = new DecimalFormat("0.00");
	
}

