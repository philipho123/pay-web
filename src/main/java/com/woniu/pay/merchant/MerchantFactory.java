package com.woniu.pay.merchant;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MerchantFactory {

	private static ClassPathXmlApplicationContext merchants;

	private static MerchantFactory factory;

	private MerchantFactory() {
		merchants = new ClassPathXmlApplicationContext("/merchant/merchants.xml");
	}

	private static MerchantFactory getInstance() {
		if (factory == null) {
			return new MerchantFactory();
		}
		return factory;
	}
	
	private Object getBean(String beanName){
		if(StringUtils.isEmpty(beanName)){
			return ObjectUtils.NULL;
		}
		if(merchants == null){
			return ObjectUtils.NULL;
		}
		return merchants.getBean(beanName);
	}
	
	public static Merchant getMerchantById(String merchantId){
		Object obj = getInstance().getBean(merchantId);
		if(obj instanceof Merchant){
			return (Merchant)obj;
		}
		return (Merchant)null;
	}

}
