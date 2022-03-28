package org.josfranmc.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HSQLServerTest {

	private HSQLDBServer serverDB = null;
	
	int index = 1;
	
	@Before
	public void init() {
		serverDB = new HSQLDBServerBuilder()
				.setDatabase(0, "mem:testdb" + String.valueOf(index))
				.setDbname(0, "tdb")
				.setAddress("127.0.0." + String.valueOf(index))
				.build();
		index++;
	}
	
	@After
	public void finalize() {
		serverDB = null;
	}
	
	
	@Test
	public void connectionTest() {
		serverDB.start();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/tdb", "SA", "");
			assertNotNull(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				serverDB.shutdownNormal();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void shutdownImmediatelyTest() {
		serverDB.start();
		serverDB.shutdownImmediately();
		waitClose(serverDB);
		assertTrue(serverDB.isNotRunning());
	}
	
	@Test
	public void shutdownCompactTest() {
		serverDB.start();
		serverDB.shutdownCompact();
		waitClose(serverDB);
		assertTrue(serverDB.isNotRunning());
	}
	
	@Test
	public void shutdownScriptTest() {
		serverDB.start();
		serverDB.shutdownScript();
		waitClose(serverDB);
		assertTrue(serverDB.isNotRunning());
	}
	
	private void waitClose(HSQLDBServer server) {
		while (!server.isNotRunning()) ;
	}
}
