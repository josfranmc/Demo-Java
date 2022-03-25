package org.josfranmc.db;

import java.util.Properties;

/**
 * Class for managing connections to Access databases.
 * @version 1.0
 * @author josfranmc
 * @see DbConnection
 */
public final class AccessConnection extends DbConnection {

	AccessConnection() {

	}

	/**
	 * Sets up a database connection to a Access database.
	 * <code>Properties</code> object must contain the following properties keys:
	 * <ul>
	 * <li>Access.url</li>
	 * <li>Access.user</li>
	 * <li>Access.password</li>
	 * </ul>
	 * @param settingProperties <code>Properties</code> object with setting data
	 * @throws IllegalArgumentException
	 * @see DbConnection
	 */
	@Override
	protected void setConnectionSetting(Properties settingProperties) {
		if (settingProperties == null) {
			throw new IllegalArgumentException("Properties type parameter must not be null");
		}

		setUrlDb(settingProperties.getProperty("Access.url"));
		setUser(settingProperties.getProperty("Access.user"));
		setPassword(settingProperties.getProperty("Access.password"));
	}
}
