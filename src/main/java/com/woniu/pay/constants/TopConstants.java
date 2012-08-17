package com.woniu.pay.constants;

public class TopConstants implements Constants {

	/** 蜗牛充值订单支付状态【待支付-0】 */
	public static final String TOP_ORDER_PAY_STATE_PENDING = "0";

	/** 蜗牛充值订单支付状态【支付失败-1】 */
	public static final String TOP_ORDER_PAY_STATE_FAILED = "1";

	/** 蜗牛充值订单支付状态【支付成功-2】 */
	public static final String TOP_ORDER_PAY_STATE_SUCCESS = "2";

	/** 蜗牛充值订单支付状态【撤销支付-3】 */
	public static final String TOP_ORDER_PAY_STATE_REVERSE = "3";

	/** 蜗牛充值订单充值状态【待充值-0】 */
	public static final String TOP_ORDER_STATE_PENDING = "0";

	/** 蜗牛充值订单充值状态【已充值-1】 */
	public static final String TOP_ORDER_STATE_OK = "1";

	/** 蜗牛充值订单充值状态【作废-9】 */
	public static final String TOP_ORDER_STATE_ABOLISH = "9";

	/** 蜗牛充值订单支付渠道返回状态【待支付-0】 */
	public static final String TOP_ORDER_OPERATOR_PENDING = "0";

	/** 蜗牛充值订单支付渠道返回状态【支付失败-1】 */
	public static final String TOP_ORDER_OPERATOR_FAILED = "1";

	/** 蜗牛充值订单支付渠道返回状态【支付成功-2】 */
	public static final String TOP_ORDER_OPERATOR_SUCCEESS = "2";

	/** 蜗牛充值订单支付渠道返回状态【撤销支付-4】 */
	public static final String TOP_ORDER_OPERATOR_REVERSAL = "4";

	/** 蜗牛充值订单支付渠道返回状态【支付成功并确认-3】 */
	public static final String TOP_ORDER_OPERATOR_CONFIRMED = "3";

	/** 蜗牛充值订单支付渠道返回状态【系统异常或参数错误-7】 */
	public static final String TOP_ORDER_OPERATOR_ERROR = "7";

	/** 蜗牛充值订单支付渠道返回状态【废止-9】 */
	public static final String TOP_ORDER_OPERATOR_ABOLITION = "9";

	public static final String PROC_JSON_PARAMTER_CODE = "RET_CODE";

	public static final String PROC_JSON_PARAMTER_ACCOUNT = "ACCOUNT";

	public static final String PROC_JSON_PARAMTER_SNAILORDERNO = "SNAIL_ORDER_NO";

	public static final String PROC_JSON_PARAMTER_AGENTORDERNO = "AGENT_ORDER_NO";

	public static final String PROC_JSON_PARAMTER_GAME_POINTS = "GAME_POINTS";

	public static final String PROC_JSON_PARAMTER_GAME_ID = "GAME_ID";

	public static final String PROC_JSON_PARAMTER_ERROR_MSG = "ERROR_MSG";

	public static final String COMMON_TRUE_OR_SUCCESS_STR = "1";

}
