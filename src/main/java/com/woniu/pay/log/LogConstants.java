package com.woniu.pay.log;

public class LogConstants {


	public static String API_LOG_APPENDER_NAME;
	public static String API_LOG_FILE_PATH;
	public static String API_LOG_PATTERN_LAYOUT;
	public static String API_LOG_PATTERN_DATE;
	
	public static String PAYMENT_LOG_FILE_PATH;
	public static String PAYMENT_LOG_PATTERN_LAYOUT;
	public static String PAYMENT_LOG_PATTERN_DATE;

	public void setAPI_LOG_APPENDER_NAME(String aPI_LOG_APPENDER_NAME) {
		API_LOG_APPENDER_NAME = aPI_LOG_APPENDER_NAME;
	}

	public void setAPI_LOG_FILE_PATH(String aPI_LOG_FILE_PATH) {
		API_LOG_FILE_PATH = aPI_LOG_FILE_PATH;
	}

	public void setAPI_LOG_PATTERN_LAYOUT(String aPI_LOG_PATTERN_LAYOUT) {
		API_LOG_PATTERN_LAYOUT = aPI_LOG_PATTERN_LAYOUT;
	}

	public void setAPI_LOG_PATTERN_DATE(String aPI_LOG_PATTERN_DATE) {
		API_LOG_PATTERN_DATE = aPI_LOG_PATTERN_DATE;
	}

	public  void setPAYMENT_LOG_FILE_PATH(String pAYMENT_LOG_FILE_PATH) {
		PAYMENT_LOG_FILE_PATH = pAYMENT_LOG_FILE_PATH;
	}

	public  void setPAYMENT_LOG_PATTERN_LAYOUT(
			String pAYMENT_LOG_PATTERN_LAYOUT) {
		PAYMENT_LOG_PATTERN_LAYOUT = pAYMENT_LOG_PATTERN_LAYOUT;
	}

	public  void setPAYMENT_LOG_PATTERN_DATE(String pAYMENT_LOG_PATTERN_DATE) {
		PAYMENT_LOG_PATTERN_DATE = pAYMENT_LOG_PATTERN_DATE;
	}

}

