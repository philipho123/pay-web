package com.woniu.pay.support;

public enum OpState {
	

	SUCCESS(1),FAIL(-1),ERROR(0);
	
	private int result;
	
	private OpState(int result){
		this.result = result;
	}
	
	public int getResult(){
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(OpState.FAIL);
	}

}
