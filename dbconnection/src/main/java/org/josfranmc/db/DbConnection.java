package org.josfranmc.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple class to get connections to a database. Setting must be in a file stored in <i>db/DbConnection.properties</i>.
 * @version 1.0
 * @author josfranmc
 */
public class DbConnection {
	
	private static final String DEFAULT_SETTING_FILE = "db/DbConnection.properties";
	
	private String driver;

	private String urlDb;

	private String user;
	
	private String password;

    
    public DbConnection() {
    	this(DEFAULT_SETTING_FILE);
    }
    
    public DbConnection(String propertiesFile) {
        Properties properties = getPropertiesFromResource(propertiesFile);
        readProperties(properties);        
    }
    
    public DbConnection(Properties properties) {
    	readProperties(properties);
    }
	
	private void readProperties(Properties properties) {
        try {
        	driver = properties.getProperty("databaseDriver");
        	urlDb = properties.getProperty("url");
        	user = properties.getProperty("user");
        	password = properties.getProperty("password");
        	
        	Class.forName(driver);
        } catch (ClassNotFoundException e) {
        	Logger.getAnonymousLogger().log(Level.WARNING, "Problems loading database driver " + driver + ". Using default mechanism.");
        }
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
			InputStream inputStream = DbConnection.class.getClassLoader().getResourceAsStream(propertiesFile);
			properties.load(inputStream);
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Properties file could not be loaded");
		}
		return properties;
	}
	
	/**
	 * @return returns a <code>Connection</code> object that represents a connection to a database
	 * @throws SQLException
	 * @see Connection
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(urlDb, user, password);
	}
	
	/**
	 * @return database url
	 */
	public String getUrlDb() {
		return urlDb;
	}

	/**
	 * @return database user used to connect
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return user password used to connect
	 */
	public String getPassword() {
		return password;
	}
}