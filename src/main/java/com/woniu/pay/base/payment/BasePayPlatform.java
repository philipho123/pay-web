package com.woniu.pay.base.payment;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Jahn
 * @since 2012-6-11
 * @version 1.0
 */
public interface BasePayPlatform <E extends BasePayOrder,F extends BasePayOrder>extends BasePlatform {
	
	  
		/**
		 * 支付平台申请创建支付订单
		 *  
		 * ☆☆该方法是支付平台发送相关参数 来创建一个支付订单 用于玩家在支付平台上支付
		 * 主要加工处理相关参数，验证加密是否正确 
		 * 创建订单是由具体的业务处理方法(如Action中)完成
		 * 
		 * @param  hashmap	原始订单参数
		 * @return NotifyInfoVO	包装后的订单参数
		 */
	    @SuppressWarnings("rawtypes")
		public E sendOrder2Snail(Map hashmap);
	    
		/**
		 * 发送订单到相应支付平台
		 *  
		 * ☆☆该方法并没有真正执行发送订单,只是将从客户端收集的订单参数
		 * 经过包装、加密(视相应的支付平台决定是否加密)后返回经过包装的订单参数,
		 * 由具体的业务处理方法(如Action中)决定什么时候真正发送到相应支付平台
		 * 
		 * @param order	原始订单参数
		 * @return HashMap	包装后的订单参数
		 */
	    public  HashMap<String,String> sendOrder2Platform(E order);
	    
	    
	    /**
		 * 支付平台同步返回(即:前台返回)订单支付信息到snail平台(页面返回)
		 * 
		 * 客户在支付平台支付结束后,返回到snail前台(页面),snail前台显示相应的支付详情
		 * @param parammap	支付平台返回的支付信息
		 * @return HashMap	支付详情
		 */
	   
		@SuppressWarnings("rawtypes")
		public  F synchNotifyPaymentInfo2Snail(Map hashmap);

	    /**
		 * 支付平台异步返回(即:后台返回)订单支付信息到snail平台(后台返回,由支付平台发起)
		 * 
		 * @param parammap	支付平台返回的支付信息
		 * @return Order	支付状态信息
		 */
	  
	    @SuppressWarnings("rawtypes")
		public  F asynchNotifyPaymentInfo2Snail(Map hashmap,String Ip);
	    
	    /**
		 * 单笔订单对帐(由snail发起)
		 * 
		 * @param parammap	原始订单参数
		 * @return Order	支付状态信息
		 */
	   
	    public  F checkSinglePayment(F Order);
	    
	    /**
		 * 我方处理支付返回信息后，返回给支付平台信息
		 * @param Order	订单信息
		 * @return String	支付状态信息
		 */
	   
	    public  String responseNotifyToPlatform(F Order);

}

