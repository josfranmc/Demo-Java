package org.josfranmc.db;

import org.hsqldb.Database;
import org.hsqldb.Server;

/**
 * Wrapper for HSQLDB server.<p>
 * In order to instantiate an object of this class must be used <code>HSQLServerBuilder</code>, which carries out a correct set up.
 * 
 * @version 1.0
 * @author josfranmc
 * @see HSQLDBServerBuilder
 * @see org.hsqldb.Server
 */
public class HSQLDBServer extends Server {

	HSQLDBServer() {

	}
	
	/**
	 * Shuts down database using Database.CLOSEMODE_NORMAL
	 */
	public void shutdownNormal() {
		this.shutdownCatalogs(Database.CLOSEMODE_NORMAL);
	}	
	
	/**
	 * Shuts down database using Database.CLOSEMODE_IMMEDIATELY
	 */
	public void shutdownImmediately() {
		this.shutdownCatalogs(Database.CLOSEMODE_IMMEDIATELY);
	}
	
	/**
	 * Shuts down database using Database.CLOSEMODE_COMPACT
	 */
	public void shutdownCompact() {
		this.shutdownCatalogs(Database.CLOSEMODE_COMPACT);
	}
	
	/**
	 * Shuts down database using Database.CLOSEMODE_SCRIPT
	 */
	public void shutdownScript() {
		this.shutdownCatalogs(Database.CLOSEMODE_SCRIPT);
	}
}
