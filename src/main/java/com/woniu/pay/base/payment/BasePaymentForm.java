package com.woniu.pay.base.payment;


import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Jahn
 * @since 2012-6-11
 * @version 1.0
 */
public abstract class BasePaymentForm implements
		BasePayPlatform<SnailPayOrder, PaymentOrder> {

	protected Logger log;

	/**
	 * 记录支付平台发送的信息 hashmap request.getParameterMap()对象，value是数组对象
	 */
	@SuppressWarnings("rawtypes")
	public void logReqPlatformParamters(Map hashmap) {
		Iterator iter = hashmap.keySet().iterator();
		log.info(this.getClass().getName() + "===begin====="
				+ new Date(System.currentTimeMillis()));
		while (iter.hasNext()) {
			Object o = iter.next();
			log.info(o.toString() + " === " + (((String[]) hashmap.get(o))[0]));
		}
		log.info(this.getClass().getName() + "===end======");

	}

	/**
	 * 记录支付平台发送的信息
	 */
	@SuppressWarnings("rawtypes")
	public void logPlatformParamters(Map hashmap) {
		Iterator iter = hashmap.keySet().iterator();
		log.info(this.getClass().getName() + "===begin====="
				+ new Date(System.currentTimeMillis()));
		while (iter.hasNext()) {
			Object o = iter.next();
			log.info(o.toString() + " === " + ((String) hashmap.get(o)));
		}
		log.info(this.getClass().getName() + "===end======");
	}

}