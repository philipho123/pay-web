package com.woniu.pay.constants;

public class PaymentConstants {

	private PaymentConstants() {
	}
	
	/** 在支付平台的交易 参数异常 --0 */
	public static final String PAYMENT_RETURN_NOTIFY_ERROR = "0";
	/** 在支付平台的交易 延迟支付 --1 */
	public static final String PAYMENT_RETURN_NOTIFY_PENDING = "1";
	/** 在支付平台的交易 支付失败--2 */
	public static final String PAYMENT_RETURN_NOTIFY_FAILED = "3";
	/** 在支付平台的交易 支付成功--3 */
	public static final String PAYMENT_RETURN_NOTIFY_SUCCESS = "5";
	/** 在支付平台的交易 支付取消 --7 */
	public static final String PAYMENT_RETURN_NOTIFY_REVERSAL = "7";
	/** 在支付平台的交易 已发货 --9 */
	public static final String PAYMENT_RETURN_NOTIFY_DELIVERY = "9";
	/** 在支付平台的交易 非法访问 --2 */
	public static final String PAYMENT_RETURN_NOTIFY_ELLEGAL_VISIT = "2";
	/** 重复充值 */
	public static final String PAYMENT_RETURN_NOTIFY_REPEAT_IMP = "4";
	/** 在支付平台的交易 校验key不正确 --3 */
	public static final String PAYMENT_RETURN_NOTIFY_ILLEGAL_KEY = "6";
	
	/** 存储支付平台地址的参数名 （蜗牛自定义） */
	public static final String PAYMENT_URL_NAME = "pay_url";
	
	/**蜗牛用户中心的地址*/ 
    public static  String SNAIL_HOMEPAGE_URL=null;

	/**蜗牛前台页面处理的地址，即支付平台引导到蜗牛页面的地址*/ 
	public static String SNAIL_REP_CLIENT_URL = null;
	/**蜗牛后端服务器处理地址，支付平台服务器异步通知支付确认消息的地址*/
	public static String SNAIL_REP_SERVER_URL = null;
	/**蜗牛处理取消支付的地址，支付平台服务器异步通知支付确认消息的地址*/
	public static String SNAIL_REP_CANCLE_URL = null;
	
	// reply client/server url for playspan
	/**蜗牛前台页面处理的地址，即支付平台引导到蜗牛页面的地址*/ 
	public static String SNAIL_REP_CLIENT_URL_FOR_PLAYSPAN = null;
	/**蜗牛后端服务器处理地址，支付平台服务器异步通知支付确认消息的地址*/
	public static String SNAIL_REP_SERVER_URL_FOR_PLAYSPAN = null;
	
	
	
	/**蜗牛LOGO图标地址*/
	public static String SNAIL_LOGO_URL = null;
	
	/**蜗牛公司名称*/
	public static String SNAIL_COMPANY_NAME = null;
	
	public  void setSNAIL_COMPANY_NAME(String sNAIL_COMPANY_NAME) {
		SNAIL_COMPANY_NAME = sNAIL_COMPANY_NAME;
	}
	public  void setSNAIL_HOMEPAGE_URL(String sNAIL_HOMEPAGE_URL) {
		SNAIL_HOMEPAGE_URL = sNAIL_HOMEPAGE_URL;
	}
	public  void setSNAIL_REP_CLIENT_URL(String sNAIL_REP_CLIENT_URL) {
		SNAIL_REP_CLIENT_URL = sNAIL_REP_CLIENT_URL;
	}
	public  void setSNAIL_REP_SERVER_URL(String sNAIL_REP_SERVER_URL) {
		SNAIL_REP_SERVER_URL = sNAIL_REP_SERVER_URL;
	}
	public  void setSNAIL_REP_CANCLE_URL(String sNAIL_REP_CANCLE_URL) {
		SNAIL_REP_CANCLE_URL = sNAIL_REP_CANCLE_URL;
	}
	public  void setSNAIL_LOGO_URL(String sNAIL_LOGO_URL) {
		SNAIL_LOGO_URL = sNAIL_LOGO_URL;
	}
	public void setSNAIL_REP_CLIENT_URL_FOR_PLAYSPAN(String sNAIL_REP_CLIENT_URL_FOR_PLAYSPAN) {
		SNAIL_REP_CLIENT_URL_FOR_PLAYSPAN = sNAIL_REP_CLIENT_URL_FOR_PLAYSPAN;
	}
	public void setSNAIL_REP_SERVER_URL_FOR_PLAYSPAN(String sNAIL_REP_SERVER_URL_FOR_PLAYSPAN) {
		SNAIL_REP_SERVER_URL_FOR_PLAYSPAN = sNAIL_REP_SERVER_URL_FOR_PLAYSPAN;
	}


	/** 我方定以的paypal ID == 19 */
	public static final int PAYMENT_PLATFORM_PAYPAL_ID = 19;
	/** 我方定以的playspan ID == 18 */
	public static final int PAYMENT_PLATFORM_PLAYSPAN_ID = 18;
	/**Moneybook支付平台id*/
	public static final int PAYMENT_PLATFORM_MONEYBOOKERS_ID = 16;
	
	/**Mopay支付平台id*/
	public static final int PAYMENT_PLATFORM_MOPAY_ID = 26;
	
	/**rixty支付平台id*/
	public static final int PAYMENT_PLATFORM_RIXTY_ID = 22;
	
	/**BoaCompray ID == 31*/
	public static final int PAYMENT_PLATFORM_BOACOMPRA_ID=31;
	
	/** go cash ID == 11*/
	public static final int PAYMENT_PLATFORM_GOCASH_ID = 11;
	
	/**CashU支付平台id*/
	public static final int PAYMENT_PLATFORM_CASHU_ID = 33;
	
	/**JCard支付平台id*/
	public static final int PAYMENT_PLATFORM_JCARD_ID = 34;
	
	
	public static final String JIU_YIN_GAME_ID = "10";

}
