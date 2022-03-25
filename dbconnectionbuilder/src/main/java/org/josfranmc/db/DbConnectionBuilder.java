package org.josfranmc.db;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.josfranmc.files.PropertiesFile;

/**
 * This class allows to build a concrete <code>DbConnection</code> object according to a specific setting.<br>
 * Configuration can be specificed by file or with an object <code>Properties</code>. By default, the file <i>db/DbHSQL.properties</i> is loaded
 * @version 1.0
 * @author josfranmc
 * @see DbConnection
 */
public class DbConnectionBuilder {

	private static final String DEFAULT_SETTING_FILE = "db/DbHSQL.properties";
	
	private Properties properties;

	
	/**
	 * Main constructor. It loads the default setting file <i>db/DbHSQL.properties</i> 
	 */
	public DbConnectionBuilder() {
		this.properties = PropertiesFile.loadPropertiesFromResource(DEFAULT_SETTING_FILE);
	}
	
	/**
	 * Builds a <code>DbConnection</code> object that allows you to get connections to a database.
	 * @return a <code>DbConnection</code> object
	 * @see DbConnection
	 */
	public DbConnection build() {
		DbConnection dbConnection = null;
		try {
			dbConnection = (DbConnection) Class.forName(properties.getProperty("ConnectionClass")).getDeclaredConstructor().newInstance();
			dbConnection.loadDriver(properties.getProperty("DatabaseDriver"));
			dbConnection.setConnectionSetting(properties);
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			throw new IllegalStateException("Impossible create class of type " + properties.getProperty("ConnectionClass"));
		}
		return dbConnection;
	}

	/**
	 * Sets a file with the setting to connect to a database. This setting will be used to configure the <code>DbConnection</code> object that will
	 * allow to get connections to database.
	 * @param filePath path (with file name) of setting file
	 * @throws IllegalArgumentException
	 * @return a reference to the <code>DbConnectionBuilder</code> object that call this method
	 */
	public DbConnectionBuilder setSettingFile(String filePath) {
		this.properties = PropertiesFile.loadPropertiesFromResource(filePath);
		return this;
	}
	
	/**
	 * Sets a <code>Properties</code> object with the setting to connect to a database. This setting will be used to configure the <code>DbConnection</code> object that will
	 * allow to get connections to database.
	 * @param properties <code>Properties</code> object with setting params 
	 * @throws IllegalArgumentException
	 * @return a reference to the <code>DbConnectionBuilder</code> object that call this method
	 */
	public DbConnectionBuilder setSettingProperties(Properties properties) {
		if (properties == null) {
			throw new IllegalArgumentException("Properties object can not be null");
		}
		this.properties = properties;
		return this;
	}
}
