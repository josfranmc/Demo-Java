package org.josfranmc.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;

/**
 * It builds and sets up a <code>HSQLServer</code> object which represents a HSQLDB database.
 * @version 1.0
 * @author josfranmc
 * @see HSQLDBServer
 */
public class HSQLDBServerBuilder {
	
	private static final Logger LOGGER = Logger.getLogger("org.josfranmc.hsql");
	
	private static final String FILE_PROPERTIES = "HSQLServer.properties";
	
	private HsqlProperties hsqlProperties;

	
	public HSQLDBServerBuilder() {
		hsqlProperties = new HsqlProperties();
	}
	
	/**
	 * @return a <code>HSQLServer</code> server object set up correctly
	 */
	public HSQLDBServer build() {
		HSQLDBServer server = null;
		try {
			server = new HSQLDBServer();
			server.setProperties(hsqlProperties);
		} catch (IOException | AclFormatException e) {
			LOGGER.log(Level.SEVERE, "Error setting properties. It could not set HsqlProperties object.");
		}
		return server;
	}

	/**
	 * Sets database path for first database (index equal zero).<p>
	 * Example path: 
	 * <ul>
	 * <li>mem:testdb</li>
	 * <li>file:testdb</li>
	 * </ul>
	 * @param dbpath database path
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setDatabase(String dbpath) {
		return setDatabase(0, dbpath);
	}
	
	/**
	 * Sets database path.<p>
	 * Example path: 
	 * <ul>
	 * <li>mem:testdb</li>
	 * <li>file:testdb</li>
	 * </ul>
	 * @param index order number in server (first database equal zero)
	 * @param dbpath database path
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setDatabase(int index, String dbpath) {
		this.hsqlProperties.setProperty("server.database." + index, dbpath);
		return this; 
	}
	
	/**
	 * Sets an alias for first database (index equal zero).
	 * @param dbname alias for the database
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setDbname(String dbname) {
		return setDbname(0, dbname);
	}
	
	/**
	 * Sets an alias for concrete database.
	 * @param index order number in server (first database equal zero)
	 * @param dbname alias for the database
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setDbname(int index, String dbname) {
		this.hsqlProperties.setProperty("server.dbname." + index, dbname);
		return this;
	}
	
	/**
	 * Sets messages displayed on console.
	 * @param silent <i>true</i> no extensive messages, <i>false</i> otherwise
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setSilent(String silent) {
		this.hsqlProperties.setProperty("server.silent", silent);
		return this;
	}
	
	/**
	 * Sets JDBC trace messages.
	 * @param trace <i>true</i> to display JDBC trace messages, <i>false</i> otherwise
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setTrace(String trace) {
		this.hsqlProperties.setProperty("server.trace", trace);
		return this;
	}
	
	/**
	 * Sets ip address of server.
	 * @param address ip aaddress
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setAddress(String address) {
		this.hsqlProperties.setProperty("server.address", address);
		return this;
	}
	
	/**
	 * Sets encrypt network stream.
	 * @param tls <i>true</i> to encrypt network stream, <i>false</i> otherwise
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setTls(String tls) {
		this.hsqlProperties.setProperty("server.tls", tls);
		return this;
	}
	
	/**
	 * Sets server as a daemon.
	 * @param daemon <i>true</i> to run server as a daemon, <i>false</i> otherwise
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setDaemon(String daemon) {
		this.hsqlProperties.setProperty("server.daemon", daemon);
		return this;
	}
	
	/**
	 * @param remote <i>true</i> to allow opening a database path remotely when the first connection is made, <i>false</i> otherwise
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setRemoteOpen(String remote) {
		this.hsqlProperties.setProperty("server.remote_open", remote);
		return this;
	}
	
	/**
	 * Sets port number for server.
	 * @param port port number
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setPort(String port) {
		this.hsqlProperties.setProperty("server.port", port);
		return this;
	}
	
	/**
	 * @param exit <i>true</i> not to call System.exit() when the database is closed, <i>false</i> otherwise
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setNoSystemExit(String exit) {
		this.hsqlProperties.setProperty("server.no_system_exit", exit);
		return this;
	}
	
	/**
	 * Load a default setting file with properties, in the format used by HyperSQL.
	 * Default file name must be <i>HSQLServer.properties</i>, in root classpath.
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setConfigFromFile() {	
		return setConfigFromFile(FILE_PROPERTIES);
	}
	
	/**
	 * Load a setting file with properties, in the format used by HyperSQL. File must be in root classpath.
	 * @return a reference to the <code>HSQLServerBuilder</code> object that call this method
	 */
	public HSQLDBServerBuilder setConfigFromFile(String file) {
		Properties properties = getPropertiesFromResource(file);
		this.hsqlProperties.addProperties(properties);
		return this;		
	}	
	
	/**
	 * It reads a properties file from classpath and returns a <code>Properties</code> object with the data read.
	 * @param propertiesFile path of file to load
	 * @return a <code>Properties</code> object with the data read
	 * @see Properties
	 */
	private Properties getPropertiesFromResource(String propertiesFile) {
		if (propertiesFile == null || propertiesFile.isEmpty()) {
			throw new IllegalArgumentException("Properties file can not be null or empty");
		}
		Properties properties = new Properties();
		try {
			InputStream inputStream = HSQLDBServerBuilder.class.getClassLoader().getResourceAsStream(propertiesFile);
			properties.load(inputStream);
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Properties file could not be loaded");
		}
		return properties;
	}	
}
