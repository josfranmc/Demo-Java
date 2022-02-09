/*
 *  Copyright (C) 2020 Jose Francisco Mena Ceca <josfranmc@gmail.com>
 *
 *  This file is part of demo-jpa.
 *
 *  demo-jpa is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  demo-jpa is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License version 3
 *  along with demo-jpa.  If not, see <https://www.gnu.org/licenses/gpl-3.0.html/>.
 */
package org.josfranmc.demo;

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
	
	private static final String DEFAULT_SETTING_FILE = "DbConnection.properties";
	
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
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(urlDb, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
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