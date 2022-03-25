package org.josfranmc.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Factory to get connections to databases.<p>
 * A Singleton pattern is used, so every call to <code>getInstance()</code> method get the same object. 
 * When a <code>DbConnectionFactory</code> object is created, the appropriate class for connecting to database is instantiated. Setting to use is read
 * from properties file which must be in a folder named <i>db</i>, in classpath<p>.
 * By default, a connection to a HSQL database is used.
 * @version 1.0
 * @author josfranmc
 * @see DbConnection
 * @see DbConnectionBuilder
 */
public class DbConnectionFactory {

	private static DbConnectionFactory instance = new DbConnectionFactory();
	
	private DbConnection dbConnection;
	
	/**
	 * Build a default <code>DbConnection</code> object.
	 */
	private DbConnectionFactory() {
		dbConnection = new DbConnectionBuilder().build();
	}
	
	/**
	 * @return a <code>Connection</code> object
	 * @throws SQLException IllegalStateException
	 * @see Connection
	 */
	public Connection getConnection() throws SQLException {
		if (dbConnection == null) {
			throw new IllegalStateException("ConnectionFactory is not initialized. No connection class instantiated.");
		}
		return dbConnection.getConnection();
	}
	
	/**
	 * Crea la instancia de la factoría que permite obtener conexiones a la base de datos. Si es la primera vez que se invoca se crea un nuevo objeto
	 * ConnectionFactory. En caso contrario, se devuelve la instancia creada previamente.
	 * @return a <code>DbConnectionFactory</code> object
	 * @see DbConnectionFactory
	 */
	public static DbConnectionFactory getInstance() {
		return instance;
	}

	public String getUrlDb() {
		return dbConnection.getUrlDb();
	}

	public String getUser() {
		return dbConnection.getUser();
	}

	public String getPassword() {
		return dbConnection.getPassword();
	}
}
