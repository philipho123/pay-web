package com.woniu.pay.util;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {
	
	  // 限制实例化
    private IPUtils(){
    }

    /**
     * 字符串转换为数字
     * @param ip ip地址
     * @return 0 ~ 4294967295的数字，如果ip地址格式错误，返回-1
     */
    public static long ipToLong(String ip){
        if(ip == null || "".equals(ip.trim())) return -1;

        String[] segment = ip.trim().split("[.]");
        if(segment.length != 4) return -1;

        long[] ips = new long[4];
        for (int i = 0; i < segment.length; i++) {
            ips[i] = Integer.parseInt(segment[i]);
            if(ips[i] < 0 || ips[i] > 255) return -1;
        }
        return ipToLong(ips);
    }

    /**
     * ip字符串根据点号分隔的数组转换为数字
     * @param ip long类型数组，长度为4
     * @return 数字格式的ip地址
     */
    public static long ipToLong(long[] ip){
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    /**
     * 数字转换为字符串
     * @param ip 数字格式的ip
     * @return 字符串格式的ip地址，例如127.0.0.1。如果超出范围(0 ~ 4294967295)，返回null。
     */
    public static String longToIp(long ip){
        if(ip < 0 || ip > 4294967295L) return null;
        StringBuffer sb = new StringBuffer("");
        //直接右移24位
        sb.append(String.valueOf(ip >>> 24));
        sb.append(".");
        //将高8位置0，然后右移16位
        sb.append(String.valueOf((ip & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((ip & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(ip  & 0x000000FF));

        return sb.toString();
    }	
    
    public static String getClientIPFromProxy(HttpServletRequest request){
		 String ip = request.getHeader("x-forwarded-for");
	     	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	             ip = request.getHeader("Proxy-Client-IP");
	      	}
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	        }
	        if(ip==null || ip.length() == 0 || ip.split("[.]").length!=4)
	        	ip ="255.255.255.255";
	        return ip;
	}
    
    /**
     * 获取客户端真实的ip地址
     * @param request
     * @return 字符串格式的ip地址，例如127.0.0.1。如果超出范围(0 ~ 4294967295)，返回null。
     * */
    public static long getRealClientIP(HttpServletRequest request){
    	String ipString = getClientIPFromProxy(request);
    	long ipLong = ipToLong(ipString);
		return ipLong;
	}

}
