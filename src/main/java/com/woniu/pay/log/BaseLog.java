package com.woniu.pay.log;


import java.io.File;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * @version 1.0
 */
public abstract class BaseLog {

	protected static File createLogFile(String filepath, String appenderName) {

		File logFolder = createLogFolder(filepath, appenderName);

		File logFile = new File(new StringBuilder(logFolder.getPath())
				.append(System.getProperty("file.separator"))
				.append(appenderName).toString());
		try {
			if (!logFile.exists())
				logFile.createNewFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return logFile;
	}

	protected static File createLogFolder(String filepath,
			String appenderName) {

		String folderPath = (new StringBuilder(filepath).append(System
				.getProperty("file.separator")).append(appenderName))
				.toString();
		File logFolder = new File(folderPath);
		try {
			if (!logFolder.exists())
				logFolder.mkdirs();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return logFolder;
	}

	protected static Logger createDailyRollingLogger(String filePath,
			String appenderName, String layout, String pattenDate) {

		Logger logger = Logger.getLogger(appenderName);
		try {
			DailyRollingFileAppender appender = new DailyRollingFileAppender(
					new PatternLayout(layout), createLogFile(filePath,
							appenderName).getPath(), pattenDate);
			appender.setName(appenderName);
			logger.addAppender(appender);
			logger.setLevel(Level.toLevel("INFO"));
			logger.setAdditivity(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logger;
	}
	
	protected static DailyRollingFileAppender createAppender(String filePath,
			String appenderName, String layout, String pattenDate) {

		DailyRollingFileAppender appender=null;
		try {
			 appender = new DailyRollingFileAppender(
					new PatternLayout(layout), createLogFile(filePath,
							appenderName).getPath(), pattenDate);
			appender.setName(appenderName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appender;
	}

}

