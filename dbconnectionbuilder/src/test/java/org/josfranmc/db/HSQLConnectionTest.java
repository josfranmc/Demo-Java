package org.josfranmc.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

public class HSQLConnectionTest {
	
	@Test
	public void setConnectionSettingTest() {
		HSQLConnection hconnection = new HSQLConnection();
		hconnection.setConnectionSetting(getProperties("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:testdb1", "SA", ""));
		
		assertEquals("Wrong database url", "jdbc:hsqldb:mem:testdb1", hconnection.getUrlDb());
		assertEquals("Wrong database user", "SA", hconnection.getUser());
		assertEquals("Wrong database user password", "", hconnection.getPassword());
		try {
			assertNotNull(hconnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void setConnectionSettingWhenWrongDriverPropertyTest() {
		HSQLConnection hconnection = new HSQLConnection();
		hconnection.setConnectionSetting(getProperties("", "jdbc:hsqldb:mem:testdb1", "SA", ""));
		assertEquals("Wrong database user", "SA", hconnection.getUser());
		try {
			assertNotNull(hconnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setConnectionSettingWhenNullParameterTest() {
		new HSQLConnection().setConnectionSetting(null);
	}
	
	@Test(expected=SQLException.class)
	public void getConnectionWhenNoConfigTest() throws SQLException {
		new HSQLConnection().getConnection();
	}

	@Test
	public void databaseTypeFileTest() {
		HSQLConnection hconnection = new HSQLConnection();
		hconnection.setConnectionSetting(getProperties("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:dbtest/sampler", "SA", ""));
		try {
			Connection connection = hconnection.getConnection();
			assertNotNull(connection);
			
			if (!isDatabaseFiles()) {
				fail("Database files not created");
			}
			shutdownDatabase(connection);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		deleteDatabaseFiles();
	}
	
	private Properties getProperties(String driver, String url, String user, String password) {
		Properties p = new Properties();
		p.setProperty("HSQL.driver", driver);
		p.setProperty("HSQL.url", url);
		p.setProperty("HSQL.user", user);
		p.setProperty("HSQL.password", password);
		return p;
	}
	
	private boolean isDatabaseFiles() {
		boolean result = true;
		File f1 = new File("dbtest/sampler.lck");
		File f2 = new File("dbtest/sampler.log");
		File f3 = new File("dbtest/sampler.properties");
		File f4 = new File("dbtest/sampler.script");
		File f5 = new File("dbtest/sampler.tmp");
		if (!f1.exists() || !f2.exists() || !f3.exists() || !f4.exists() || !f5.exists()) {
			result = false;
		}
		return result;
	}
	
	private void deleteDatabaseFiles() {
		new File("dbtest/sampler.lck");
		new File("dbtest/sampler.log").delete();
		new File("dbtest/sampler.properties").delete();
		new File("dbtest/sampler.script").delete();
		new File("dbtest/sampler.tmp").delete();
		new File("dbtest").delete();
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
