package com.woniu.pay.service;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woniu.pay.model.AgencyOrder;
import com.woniu.pay.service.exception.ServiceException;

@Service
@Transactional
public class AgencyOrderService extends BaseService<AgencyOrder, Long>{

	public AgencyOrder createOrder(AgencyOrder order) {
		try {
			save(order);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("创建订单失败", e);
		}
		return order;
	}

	public void imprestWithSnailOrderNo(String snailOrderNo, Integer paymentId, String paymentOrderNo, String paymentState) {
		AgencyOrder order = new AgencyOrder();
		order.setId(NumberUtils.toLong(snailOrderNo));
		order.setPaymentId(paymentId);
		order.setPaymentOrderNo(paymentOrderNo);
		//order.setPaymentAmount(amount);
		//order.setPaymentCurrency(currency);
		order.setPaymentState(paymentState);
		order.setState("1");//
		try {
			save(order);
		} catch (Exception e) {
			throw new ServiceException("订单充值失败", e);
		}
	}
}
