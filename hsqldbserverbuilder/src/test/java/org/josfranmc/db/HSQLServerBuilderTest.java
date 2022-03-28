package org.josfranmc.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HSQLServerBuilderTest {

	@Test
	public void createDatabaseServerTest() {
		HSQLDBServer serverDB = new HSQLDBServerBuilder()
				.setDatabase(1, "mem:testdb")
				.setDbname(1, "tdb")
				.setPort("9028")
				.build();
		try {
			serverDB.start();
			assertEquals("Wrong database path", "mem:testdb", serverDB.getDatabasePath(1, true));
			assertEquals("Wrong database name", "tdb", serverDB.getDatabaseName(1, true));
			assertEquals("Wrong port", 9028, serverDB.getPort());
		} finally {
			serverDB.shutdownImmediately();
		}		
	}
	
	@Test
	public void setDatabaseTest() {
		HSQLDBServer serverDB = new HSQLDBServerBuilder()
				.setDatabase("mem:probe")
				.setDbname("prb")
				.build();
		try {
			serverDB.start();
			assertEquals("Wrong database path (index 0)", "mem:probe", serverDB.getDatabasePath(0, true));
			assertEquals("Wrong database name (index 0)", "prb", serverDB.getDatabaseName(0, true));
		} finally {
			serverDB.shutdownImmediately();
		}	
	}
	
	@Test
	public void setSilentWhenTrueTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(1);
		HSQLDBServer serverDB = builder.setSilent("true").build();
		assertTrue(serverDB.isSilent());
	}
	
	@Test
	public void setSilentWhenFalseTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(2);
		HSQLDBServer serverDB = builder.setSilent("false").build();	
		assertFalse(serverDB.isSilent());	
	}
	
	@Test
	public void setTraceWhenTrueTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(3);
		HSQLDBServer serverDB = builder.setTrace("true").build();
		assertTrue(serverDB.isTrace());
	}
	
	@Test
	public void setTraceWhenFalseTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(4);
		HSQLDBServer serverDB = builder.setTrace("false").build();
		assertFalse(serverDB.isTrace());	
	}
	
	@Test
	public void setTlsWhenTrueTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(5);
		HSQLDBServer serverDB = builder.setTls("true").build();
		assertTrue(serverDB.isTls());
	}
	
	@Test
	public void setTlsWhenFalseTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(6);
		HSQLDBServer serverDB = builder.setTls("false").build();
		assertFalse(serverDB.isTls());	
	}
	
	@Test
	public void setNoSystemExitWhenTrueTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(5);
		HSQLDBServer serverDB = builder.setNoSystemExit("true").build();
		assertTrue(serverDB.isNoSystemExit());
	}
	
	@Test
	public void setNoSystemExitWhenFalseTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(6);
		HSQLDBServer serverDB = builder.setNoSystemExit("false").build();
		assertFalse(serverDB.isNoSystemExit());	
	}
	
	@Test(expected=SQLException.class)
	public void setRemoteOpenWhenFalseTest() throws SQLException {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(7);
		HSQLDBServer serverDB = builder.setRemoteOpen("false").build();
		serverDB.start();
		try {
			DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/ndb;mem:newdb", "SA", "");
		} finally {
			serverDB.shutdownImmediately();
		}
	}
	/*
	@Test
	public void setRemoteOpenWhenTrueTest() throws SQLException {
		HSQLServerBuilder builder = getHsqlServerBuilderForTest(8);
		HSQLServer serverDB = builder.setRemoteOpen("true").build();
		serverDB.start();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/ndb;mem:newdb", "SA", "");
			assertNotNull(connection);
		} finally {
			serverDB.signalCloseAllServerConnections();
			serverDB.shutdownImmediately();
		}
	}
	*/
	@Test
	public void setAddressTest() {
		HSQLDBServerBuilder builder = getHsqlServerBuilderForTest(9);
		HSQLDBServer serverDB = builder.setAddress("192.168.1.30").build();
		assertEquals("Wrong address", "192.168.1.30", serverDB.getAddress());
	}
	
	@Test
	public void configFromDefaultFileTest() {
		HSQLDBServer serverDB = new HSQLDBServerBuilder()
				.setConfigFromFile()
				.build();
		try {
			serverDB.start();
			assertEquals("Wrong database path", "mem:sampledb;hsqldb.tx=mvcc", serverDB.getDatabasePath(0, true));
			assertEquals("Wrong database name", "sdb", serverDB.getDatabaseName(0, true));
		} finally {
			serverDB.shutdownImmediately();
		}
	}
	
	@Test
	public void configFromFileTest() {
		HSQLDBServer serverDB = new HSQLDBServerBuilder()
				.setConfigFromFile("HSQLServerTest.properties")
				.build();
		try {
			serverDB.start();
			assertEquals("Wrong database path", "mem:sampletest", serverDB.getDatabasePath(0, true));
			assertEquals("Wrong database name", "spts", serverDB.getDatabaseName(0, true));
		} finally {
			serverDB.shutdownImmediately();
		}
	}
	
	private HSQLDBServerBuilder getHsqlServerBuilderForTest(int n) {
		return  new HSQLDBServerBuilder()
				.setDatabase(0, "mem:testdb" + String.valueOf(n))
				.setDbname(0, "tdb");
	}
}
