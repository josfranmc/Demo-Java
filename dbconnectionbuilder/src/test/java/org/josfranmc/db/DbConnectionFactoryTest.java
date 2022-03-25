package org.josfranmc.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Test;

public class DbConnectionFactoryTest {
	
	@After
	public void resetDbConnectionFactorySingleton()
			throws NoSuchFieldException, SecurityException, NoSuchMethodException, 
			       IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException 
	{
	    Field instance = DbConnectionFactory.class.getDeclaredField("instance");
	    instance.setAccessible(true);

	    Constructor<DbConnectionFactory> constructor = DbConnectionFactory.class.getDeclaredConstructor();
	    constructor.setAccessible(true);
	    instance.set(null, constructor.newInstance());
	}
	
	@Test
	public void getInstanceTest() {
		DbConnectionFactory cf1 = DbConnectionFactory.getInstance();
		DbConnectionFactory cf2 = DbConnectionFactory.getInstance();
		assertSame("Diferent instances", cf1, cf2);
	}
	
	@Test
	public void getConnectionTest() {
		Connection connection = null;
		try {
			DbConnectionFactory cf = DbConnectionFactory.getInstance();
			assertEquals("Error fetching data from Test table", "jdbc:hsqldb:mem:tempdb", cf.getUrlDb());
			assertEquals("Error fetching data from Test table", "SA", cf.getUser());
			assertEquals("Error fetching data from Test table", "", cf.getPassword());
			connection = cf.getConnection();
			assertNotNull(connection);
			
			createTable(connection);
			insertData(connection, "example");
			
			assertEquals("Error fetching data from Test table", "example", getData(connection));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Default connection test Failed");
		} finally {
			try {
				if (connection != null) {
					shutdownDatabase(connection);
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				fail("Default connection test Failed (2)");
			}
		}
	}

	private void createTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(
				"CREATE TABLE Test (" + 
                " ID varchar(10) PRIMARY KEY," + 
 		        " name varchar(30) NULL);"
 		);
		statement.close();
	}
	
	private void insertData(Connection connection, String value) throws SQLException {
		PreparedStatement pstatement = null;
		pstatement = connection.prepareStatement("INSERT INTO Test VALUES (?, ?)");
   	 	pstatement.setString(1, "1");
   	 	pstatement.setString(2, value);
   	 	pstatement.executeUpdate();
   	 	pstatement.close();
	}
	
	private String getData(Connection connection) throws SQLException {
		PreparedStatement pstatement = null;
		ResultSet resultSet = null;
		String value = null;

		pstatement = connection.prepareStatement("SELECT * FROM Test WHERE id = ?");
		pstatement.setString(1, "1");
		resultSet = pstatement.executeQuery();
		if (resultSet.next()) {
			value = resultSet.getString(2);
		}
   	 	return value;
	}

	private void shutdownDatabase(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("SHUTDOWN");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
