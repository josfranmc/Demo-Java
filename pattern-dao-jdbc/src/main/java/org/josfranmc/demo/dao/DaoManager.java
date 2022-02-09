package org.josfranmc.demo.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoManager implements IDaoManager {

	private Connection connection;
	
	public DaoManager(Connection connection) {
        this.connection = connection;
	}

	@Override
	public void beginTransaction() throws SQLException {
		connection.setAutoCommit(false);
	}

	@Override
	public void commitTransaction() throws SQLException {
		connection.commit();
		connection.setAutoCommit(true);
	}

	@Override
	public void rollbackTransaction() throws SQLException {
		connection.rollback();
		connection.setAutoCommit(true);
	}

	@Override
	public IClientDao getClientDao() {
		return new ClientDaoJdbc(connection);
	}
	
	@Override
	public IOrderDao getOrderDao() {
		return new OrderDaoJdbc(connection);
	}

	@Override
	public IItemDao getItemDao() {
		return new ItemDaoJdbc(connection);
	}
	
	@Override
	public void close() throws Exception {
		connection.close();
	}	
}