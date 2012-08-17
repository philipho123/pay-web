package com.woniu.pay.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @version 1.0
 */
public class StringToolKit {
	/**
	 * String2Long
	 * */
	public static Long String2Long(String str) {
		if (!StringUtils.isNumeric(str))
			return 0L;

		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	/**
	 * String2Integer
	 * */
	public static Integer String2Integer(String str) {
		if (!StringUtils.isNumeric(str))
			return 0;

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * String2Double
	 * */
	public static Double String2Double(String str) {

		if (!StringUtils.isNumeric(str))
			return 0D;

		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0D;
		}
	}

	/**
	 * Object2String
	 * */
	public static String Object2String(Object obj) {
		return obj == null ? null : obj.toString();

	}

	/**
	 * Object2Integer
	 * */
	public static Integer Object2Integer(Object obj) {

		if (!StringUtils.isNumeric(Object2String(obj)))
			return 0;

		try {
			return Integer.parseInt(obj.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Object2Integer
	 * */
	public static Long Object2Long(Object obj) {

		if (!StringUtils.isNumeric(Object2String(obj)))
			return 0L;

		try {
			return Long.parseLong(obj.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	/**
	 * Object2Integer
	 * */
	public static Date Object2Date(Object obj) {
			return obj == null ? null : (Date)obj;
	}
	/**
	 * Object2Integer
	 * */
	public static Double Object2Double(Object obj) {

		if (!StringUtils.isNumeric(Object2String(obj)))
			return 0D;

		try {
			return Double.parseDouble(obj.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0D;
		}
	}
	/**
	 * nullToEmptyStr
	 * 字符串如果是NULL 则转换成空值
	 * @param str
	 * @return String
	 * */
	public static String nullToEmptyStr(String str){
		return str==null?"":str;
	}
	/**
	 * nullToEmptyStr
	 * 字符串如果是NULL 则转换成空值
	 * @param obj
	 * @return String
	 * */
	public static String nullToEmptyStr(Object obj){
		return nullToEmptyStr(Object2String(obj));
		 
	}
	
	/**
	 * requestMapParamterToStr
	 * request.getParameterMap()获取的对象值转换成字符串
	 * @param obj
	 * @return String
	 * */
	public static String requestMapParamterToStr(Object obj){
			if(obj==null)
					return null;
			return ((String[])obj)[0];

	}
	
	/**
	 * requestMapParamterToEmptyStr
	 * request.getParameterMap()获取的对象值转换成非NULL字符串
	 * 
	 * @param obj
	 * @return String
	 * */
	public static String requestMapParamterToEmptyStr(Object obj){
			return nullToEmptyStr(requestMapParamterToStr(obj));
	}
	public static boolean is4IP(String ip) {
		if (ip == null || "".equals(ip))
			return false;
		StringBuffer sbExp = new StringBuffer();
		sbExp.append("^([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])")
				.append(".([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])")
				.append(".([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])")
				.append(".([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])$");

		Pattern pattern = Pattern.compile(sbExp.toString());
		Matcher isIP = pattern.matcher(ip);
		return isIP.matches();
	}

	public static boolean is6IP(String ip) {
		if (ip == null || "".equals(ip))
			return false;
		StringBuffer sbExp = new StringBuffer();
		sbExp.append("^([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])")
				.append(".([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])")
				.append(".([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])")
				.append(".([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])")
				.append(".([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])")
				.append(".([0-9]{1,2}|1[0-9]{1,2}|2[0-4][0-9]|25[0-5])$");

		Pattern pattern = Pattern.compile(sbExp.toString());
		Matcher isIP = pattern.matcher(ip);
		return isIP.matches();
	}

	/**
	 * 判断字符串是否是小数的数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		if (StringUtils.isBlank(str))
			return false;
		Pattern pattern = Pattern.compile("[0-9]*.[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否是两位小数的数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean is2BitFloat(String str) {
		if (StringUtils.isBlank(str))
			return false;
		Pattern pattern = Pattern.compile("[0-9]*.[0-9]{2}");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * urlDecoder
	 * */
	public static String decoderParameter(String str) {

		if (StringUtils.isEmpty(str))
			return "";
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}

