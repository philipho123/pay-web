package com.woniu.pay.mvc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.woniu.pay.merchant.Merchant;
import com.woniu.pay.merchant.MerchantFactory;
import com.woniu.pay.merchant.web.AbstractWebAction;

public abstract class MerchantAction extends AbstractWebAction {

	public String handleMerchantId(HttpServletRequest request, HttpServletResponse response) {
		String merchantId = getMerchantId();
		if (StringUtils.isEmpty(merchantId)) {
			return error(request, "Invalid merchant ID.", getErrorPage());
		}
		Merchant merc = MerchantFactory.getMerchantById(merchantId);
		if (merc == null) {
			return error(request, "Invalid merchant ID.", getErrorPage());
		}
		return null;
	}

	protected abstract String getMerchantId();

	protected abstract String getErrorPage();

}
