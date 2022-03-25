package org.josfranmc.db;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class DbConnectionTest {

	@Test
	public void dbConnectionDefaultConstructorTest() {
		DbConnection dbConnection = new DbConnection();
		try {
			assertNotNull(dbConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void dbConnectionStringConstructorTest() {
		DbConnection dbConnection = new DbConnection("db/DbConnectionAlt.properties");
		try {
			assertNotNull(dbConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void dbConnectionPropertiesConstructorTest() {
		Properties properties = new Properties();
		properties.setProperty("databaseDriver", "org.hsqldb.jdbcDriver");
		properties.setProperty("url", "jdbc:hsqldb:mem:tempdbAlt2");
		properties.setProperty("user", "SA");
		properties.setProperty("password", "");
    	
		DbConnection dbConnection = new DbConnection();
		try {
			assertNotNull(dbConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void dbConnectionStringConstructorTest2() {
		new DbConnection("db/BadFile.properties");
	}
}
