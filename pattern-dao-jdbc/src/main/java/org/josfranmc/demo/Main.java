package org.josfranmc.demo;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.josfranmc.demo.dao.DaoManager;
import org.josfranmc.demo.dao.IClientDao;
import org.josfranmc.demo.dao.IDaoManager;
import org.josfranmc.demo.domain.Client;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);
	
	protected static DbConnection dbConnection = new DbConnection();
	
	public static void main(String[] args) {
		
		Database.initialize();
		
		logger.info("[DEMODAO] With transaction...");
        
		Connection connection = dbConnection.getConnection();
        
        try (IDaoManager daoManager = new DaoManager(connection)) {
            try {
                daoManager.beginTransaction();
                IClientDao clientdDao = daoManager.getClientDao();
                Client client = clientdDao.createElement();
                client.setName("John Doe");
                clientdDao.save(client);
                client = clientdDao.createElement();
                client.setName("Jane Doe");
                clientdDao.save(client);
                client = clientdDao.getElementById(2L);
                logger.info(client.toString());
                daoManager.commitTransaction();
            } catch (SQLException e) {
                daoManager.rollbackTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        logger.info("[DEMODAO] Without transaction");
        
        connection = dbConnection.getConnection();
                
        try (IDaoManager daoManager = new DaoManager(connection)) {
        	IClientDao clientdDao = daoManager.getClientDao();
        	Client client = clientdDao.createElement();
        	client.setName("James Doe");
        	Long key = daoManager.getClientDao().save(client);
        	client = clientdDao.getElementById(key);
        	logger.info(client.toString());
        	client.setName("William Doe");
        	daoManager.getClientDao().update(client);
        	logger.info(client.toString());
        } catch (Exception e) {
        	e.printStackTrace();
       	}
	}
}