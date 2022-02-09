package org.josfranmc.demo.dao;

import java.sql.SQLException;

public interface IDaoManager extends AutoCloseable {
	
	void beginTransaction() throws SQLException;

	void commitTransaction() throws SQLException;

	void rollbackTransaction() throws SQLException;
	
	IClientDao getClientDao();
	
	IOrderDao getOrderDao();
	
	IItemDao getItemDao();
}
