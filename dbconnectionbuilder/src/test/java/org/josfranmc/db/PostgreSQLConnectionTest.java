package org.josfranmc.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class PostgreSQLConnectionTest {
	
	@Test
	public void setConnectionSettingTest() {
		PostgreSQLConnection pconnection = new PostgreSQLConnection();
		pconnection.setConnectionSetting(getProperties("org.postgresql.Driver"));
		
		assertEquals("Wrong database url", "jdbc:postgresql://localhost:5432/tester", pconnection.getUrlDb());
		assertEquals("Wrong database user", "test", pconnection.getUser());
		assertEquals("Wrong database user password", "test", pconnection.getPassword());
		try {
			assertNotNull(pconnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void setConnectionSettingWhenWrongDriverPropertyTest() {
		PostgreSQLConnection pconnection = new PostgreSQLConnection();
		pconnection.setConnectionSetting(getProperties("bad.Driver"));
		assertEquals("Wrong database url", "jdbc:postgresql://localhost:5432/tester", pconnection.getUrlDb());
		try {
			assertNotNull(pconnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setConnectionSettingWhenNullParameterTest() {
		new PostgreSQLConnection().setConnectionSetting(null);
	}
	
	@Test(expected=SQLException.class)
	public void getConnectionWhenNoConfigTest() throws SQLException {
		new PostgreSQLConnection().getConnection();
	}

	private Properties getProperties(String driver) {
		Properties p = new Properties();
		p.setProperty("PostgreSQL.host", "localhost");
		p.setProperty("PostgreSQL.port", "5432");
		p.setProperty("PostgreSQL.dbname", "tester");
		p.setProperty("PostgreSQL.user", "test");
		p.setProperty("PostgreSQL.password", "test");		
		return p;
	}
}
