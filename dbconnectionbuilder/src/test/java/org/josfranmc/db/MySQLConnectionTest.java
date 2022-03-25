package org.josfranmc.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class MySQLConnectionTest {
	
	@Test
	public void setConnectionSettingTest() {
		MySQLConnection mconnection = new MySQLConnection();
		mconnection.setConnectionSetting(getProperties("com.mysql.cj.jdbc.Driver"));
		
		assertEquals("Wrong database url", "jdbc:mysql://localhost:3306/world?useUnicode=true", mconnection.getUrlDb());
		assertEquals("Wrong database user", "tester", mconnection.getUser());
		assertEquals("Wrong database user password", "test", mconnection.getPassword());
		try {
			assertNotNull(mconnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void setConnectionSettingWhenWrongDriverPropertyTest() {
		MySQLConnection mconnection = new MySQLConnection();
		mconnection.setConnectionSetting(getProperties("bad.Driver"));
		assertEquals("Wrong database url", "jdbc:mysql://localhost:3306/world?useUnicode=true", mconnection.getUrlDb());
		try {
			assertNotNull(mconnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setConnectionSettingWhenNullParameterTest() {
		new MySQLConnection().setConnectionSetting(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void getConnectionWhenNoConfigTest() throws SQLException {
		new MySQLConnection().getConnection();
	}

	private Properties getProperties(String driver) {
		Properties p = new Properties();
		p.setProperty("MySQL.driver", driver);
		p.setProperty("MySQL.host", "localhost");
		p.setProperty("MySQL.port", "3306");
		p.setProperty("MySQL.dbname", "world");
		p.setProperty("MySQL.params", "useUnicode=true");
		p.setProperty("MySQL.ssl", "FALSE");
		p.setProperty("MySQL.user", "tester");
		p.setProperty("MySQL.password", "test");		
		return p;
	}
}
