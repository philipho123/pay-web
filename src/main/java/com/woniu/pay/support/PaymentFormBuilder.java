package com.woniu.pay.support;


import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PaymentFormBuilder {

	private static final Logger logger = Logger.getLogger(PaymentFormBuilder.class);

	private PaymentForm payForm;
	private String json;

	public PaymentFormBuilder(final PaymentForm payForm) {
		this.payForm = payForm;
	}

	public String getHtml(String targetId) {
		Map<String, String> hashmap = payForm.getMembers();
		String payUrl = payForm.getPayUrl();

		if (hashmap != null && !StringUtils.isEmpty(payUrl)) {
			StringBuffer strForm = new StringBuffer();
			strForm.append("<form name=\"payment_form\" id =\"payment_form\"  method=\"post\"");
			if (!StringUtils.isEmpty(targetId)) {
				strForm.append(" target=\"").append(targetId).append("\"");
			}
			strForm.append(" action=\"").append(payUrl).append("\" > \n");
			Iterator<String> iter = hashmap.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				if (!key.equals("pay_url")) {
					strForm.append("<input type=\"hidden\" name=\"").append(key).append("\" value=\"")
							.append((String) hashmap.get(key)).append("\" >\n");
				}
			}
			strForm.append("</form>");
			return strForm.toString();
		}
		return null;
	}
	
	public String getJson(){
		Map<String, String> hashmap = payForm.getMembers();
		if(hashmap != null && !hashmap.isEmpty()){
			StringBuilder sb = new StringBuilder("{\n");
			Iterator<String> iter = hashmap.keySet().iterator();
			while(iter.hasNext()){
				String key = (String)iter.next();
				if(!key.equals("pay_url")){
					sb.append("\"").append(key).append("\" : \"").append((String)hashmap.get(key)).append("\",\n");
				}
			}
			if(sb.toString().endsWith(",\n")){
				sb.substring(0, sb.length()-2);
			}
			json = sb.substring(0, sb.length()-2) + "\n}";
			return json;
		}
		return null;
	}

	public void printHtml(JspWriter out, HttpServletRequest req, String targertId) {
		if (req == null)
			return;
		String strHtml = getHtml(targertId);
		if (strHtml != null) {
			try {
				out.print(strHtml);
			} catch (Exception ex) {
				logger.error("Error: JspWriter - PayFormBuilder.printHtml");
			}
		}
	}

	public Map<String, String> getMembers() {
		return payForm.getMembers();
	}

	public String getURL() {
		Map<String, String> hashmap = payForm.getMembers();
		String payUrl = payForm.getPayUrl();

		if (hashmap != null && !StringUtils.isEmpty(payUrl)) {
			StringBuffer strhtmlform = new StringBuffer();
			strhtmlform.append(payUrl).append("?");
			Iterator<String> iter = hashmap.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				if (!key.equals("pay_url")) {
					strhtmlform.append(key).append("=").append((String) hashmap.get(key)).append("&");
				}
			}
			String URL = strhtmlform.toString();
			return URL.substring(0, URL.length() - 1);
		}
		return null;
	}

}

