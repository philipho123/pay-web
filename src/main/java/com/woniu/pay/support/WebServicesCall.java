package com.woniu.pay.support;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.client.Client;

import com.woniu.pay.util.StringToolKit;

/**
 * @version 1.0
 */
public class WebServicesCall {
	
	/**
	 * HTTP 调用WebService地址
	 * @param urlString
	 * 			WebService URL地址
	 * @param methodName 
	 * 			方法名
	 * @param objsParamter
	 * 			参数值【数组】
	 * @return 返回字符串
	 * */
	public static String callServices(String urlString,String methodName,Object[] objsParamter){
		if(StringUtils.isBlank(urlString)||StringUtils.isBlank(methodName))
			return null;
		try {
	
			Client client = new Client(new java.net.URL(urlString));
		
			Object[] results = client.invoke(methodName, objsParamter);
			client.close();
			return StringToolKit.Object2String(results[0]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}// TODO Auto-generated method stub
		return null;
	}
	/**
	 * HTTP post请求URL地址
	 * @param urlString
	 * 			URL地址
	 * @param paramMap 
	 * 			参数键值对
	 * @return 返回字符串
	 * */
	public static String callHttpPost(String urlString,HashMap<String,String> paramMap ){
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();
			for (int i = 3; i > 0 && uc == null; i--) {
				uc = (HttpURLConnection) url.openConnection();
			}

			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.setRequestMethod("POST");
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded; charset=utf-8");
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			Iterator <String>iter = paramMap.keySet().iterator();
			StringBuffer sbParam =new StringBuffer();
			sbParam.append("&1=1");
			while (iter.hasNext()) {
				String name = iter.next();
				sbParam.append("&").append(name).append("=").append(paramMap.get(name));
			}
			pw.print(sbParam.toString());
			pw.close();
			StringBuffer sbReturn =new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));
			for (String inputLine=""; StringUtils.isNotBlank(inputLine = in.readLine());) {
				sbReturn.append(inputLine.toString()).append(" \n");
			}
			in.close();
			return sbReturn.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

