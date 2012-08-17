package com.woniu.pay.mvc.validator;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.woniu.pay.merchant.web.AbstractWebAction;

@Component
public class ParameterValidator extends AbstractWebAction {

	private static Set<String> requiredParameters;
	
	static {
		requiredParameters = new TreeSet<String>();
		requiredParameters.add("accountid");
		requiredParameters.add("amount");
		requiredParameters.add("currency");
		requiredParameters.add("merchantid");
		requiredParameters.add("orderid");
		requiredParameters.add("paymentid");
		//requiredParameters.add("paymenttype");
		requiredParameters.add("points");
		requiredParameters.add("mdsig");
		//requiredParameters.add("serverurl");
		//requiredParameters.add("clienturl");
		requiredParameters = Collections.unmodifiableSet(requiredParameters);
	}

	public ParameterValidator() {
	}

	public String validate() {
		for (String param : requiredParameters) {
			if (StringUtils.isEmpty(getRequestParameter(param))) {
				return param + " is required.";
			}
		}
		return null;
	}

}
