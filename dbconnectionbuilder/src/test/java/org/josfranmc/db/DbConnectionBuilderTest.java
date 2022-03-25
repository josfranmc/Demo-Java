package org.josfranmc.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class DbConnectionBuilderTest {
	
	@Test
	public void defaultBuildTest() {
		DbConnection dbConnection = new DbConnectionBuilder().build();
		try {
			assertNotNull(dbConnection.getConnection());
			assertEquals("Wrong database url", "jdbc:hsqldb:mem:tempdb", dbConnection.getUrlDb());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setSettingFileWhenWrongFileTest() {
		new DbConnectionBuilder().setSettingFile("wrong_file").build();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setSettingFileWhenNullFileTest() {
		new DbConnectionBuilder().setSettingFile(null).build();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setSettingFileWhenEmptyFileTest() {
		new DbConnectionBuilder().setSettingFile("").build();
	}
	
	@Test(expected=IllegalStateException.class)
	public void setSettingFileWhenNoConnectionClassPropertyTest() {
		new DbConnectionBuilder().setSettingFile("db/NoConnectionClass.properties").build();
	}
	
	@Test
	public void setSettingFileWhenBadDriverPropertyTest() {
		DbConnection dbc = new DbConnectionBuilder().setSettingFile("db/BadDriver.properties").build();
		try {
			assertNotNull(dbc.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void setSettingPropertiesTest() {
		Properties p = new Properties();
		p.setProperty("ConnectionClass", "org.josfranmc.db.HSQLConnection");
		p.setProperty("DatabaseDriver", "org.hsqldb.jdbcDriver");
		p.setProperty("HSQL.url", "jdbc:hsqldb:mem:proper");
		p.setProperty("HSQL.user", "SA");
		p.setProperty("HSQL.password", "");
		DbConnection c = new DbConnectionBuilder().setSettingProperties(p).build();
		assertEquals("Wrong database url", "jdbc:hsqldb:mem:proper", c.getUrlDb());
		assertEquals("Wrong database user", "SA", c.getUser());
		assertEquals("Wrong database user password", "", c.getPassword());
	}
	
	@Test
	public void setSettingPropertiesWhenNullParameterTest() {
		DbConnectionBuilder b = null;
		try {
			b = new DbConnectionBuilder();
			b.setSettingProperties(null);
		} catch (IllegalArgumentException e) {
			
		}
		assertNotNull(b.build());
	}
}
