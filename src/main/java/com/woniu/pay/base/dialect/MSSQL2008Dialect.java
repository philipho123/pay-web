package com.woniu.pay.base.dialect;

import org.hibernate.dialect.SQLServerDialect;

public class MSSQL2008Dialect extends SQLServerDialect{
	
	public MSSQL2008Dialect(){
		  super();    
	      registerHibernateType(-9, "string");   
	}

}
