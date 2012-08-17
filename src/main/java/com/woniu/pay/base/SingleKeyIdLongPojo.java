package com.woniu.pay.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.AccessType;

/**
 * 字段名是N_ID 数据类型Long的POJO父类
 * @version 1.0
 */
@MappedSuperclass
public abstract class SingleKeyIdLongPojo extends SingleKeyPojo<Long>{

	private static final long serialVersionUID = 1319050684492359405L;
	@Id
	@AccessType(value = "property")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "N_ID")
	protected Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}