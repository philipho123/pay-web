package com.woniu.pay.service.exception;

public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = -1212281750434426091L;

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable e){
		super(message, e);
	}

}
