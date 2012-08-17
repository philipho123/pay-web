package com.woniu.pay.base;

import javax.persistence.Id;



/**
 * 所有非复合主键Pojo的基础接口
 */

public abstract class SingleKeyPojo <T>implements Pojo {
	
	private static final long serialVersionUID = 5529632501332744745L;
	@Id
	protected T id;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}

