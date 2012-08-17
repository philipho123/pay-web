package com.woniu.pay.support;


import java.sql.Types;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * @author jahn
 * @since 2012-03-21
 */
public class JdbcHelper {

	public static int translateType(Object o) {
		if (o == null) {
			return Types.VARCHAR;
		} else if (o instanceof String) {
			return Types.VARCHAR;
		} else if (o instanceof Integer) {
			return Types.INTEGER;
		} else if (o instanceof Long) {
			return Types.BIGINT;
		} else if (o instanceof Date) {
			return Types.DATE;
		}
		return Types.NVARCHAR;
	}

	public static String removeSelect(String queryString) {
		Assert.hasText(queryString);
		int fromIdx = queryString.toLowerCase().indexOf(" from ");
		Assert.isTrue(fromIdx != -1, "queryString [" + queryString
				+ "] must has a keyword 'from'.");
		return queryString.substring(fromIdx);
	}

	public static String removeOrders(String queryString) {
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

