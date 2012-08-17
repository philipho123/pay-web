package com.woniu.pay.log;


import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @author Jahn
 * @since 2012-6-26
 * @version 1.0
 */
public class PaymentLog extends BaseLog {
	private static HashMap<String,DailyRollingFileAppender> APPENDER_MAP=null;
	DailyRollingFileAppender API_LOG_APPENDER = null; 
	private static String PAYMENT_LOG_PROPERTIES_FILE_PATH = "log4j";
	private static String PAYMENT_LOG_FOLDER_PATH=null;
	private static String PAYMENT_LOG_PATTERN_LAYOUT=null;
	private static String PAYMENT_LOG_PATTERN_DATE=null;

	
	public static Logger getLogInstance(String name) {
		if (APPENDER_MAP==null){
			loadLogConfigParams();
			APPENDER_MAP=new HashMap<String,DailyRollingFileAppender>();
		}
			
		if(APPENDER_MAP.get(name)==null){
			APPENDER_MAP.put(name, createAppender(
					PAYMENT_LOG_FOLDER_PATH,
					name,
					PAYMENT_LOG_PATTERN_LAYOUT,
					PAYMENT_LOG_PATTERN_DATE));
		}
		Logger logger = Logger.getLogger(name);
		logger.addAppender(APPENDER_MAP.get(name));
		logger.setLevel(Level.toLevel("INFO"));
		logger.setAdditivity(false);
		return logger;
		
	}
	private static void loadLogConfigParams() {
		ResourceBundle rb = ResourceBundle
				.getBundle(PAYMENT_LOG_PROPERTIES_FILE_PATH);
		PAYMENT_LOG_FOLDER_PATH = rb.getString("PAYMENT_LOG_FOLDER_PATH")==null?"paymentlogs":rb.getString("PAYMENT_LOG_FOLDER_PATH");
		PAYMENT_LOG_PATTERN_LAYOUT = rb.getString("PAYMENT_LOG_PATTERN_LAYOUT")==null?"%-d{yyyy-MM-dd HH:mm:ss} [%c]-[%p] %m%n":rb.getString("PAYMENT_LOG_PATTERN_LAYOUT");
		PAYMENT_LOG_PATTERN_DATE = rb.getString("PAYMENT_LOG_PATTERN_DATE")==null?"'_'yyyy-MM-dd'.log'":rb.getString("PAYMENT_LOG_PATTERN_DATE");
	}

}

