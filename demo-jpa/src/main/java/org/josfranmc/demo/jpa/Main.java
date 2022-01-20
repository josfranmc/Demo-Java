package org.josfranmc.demo.jpa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.logging.log4j.Logger;
import org.josfranmc.demo.jpa.domain.Client;
import org.josfranmc.demo.jpa.domain.Item;
import org.josfranmc.demo.jpa.domain.Order;
import org.apache.logging.log4j.LogManager;

public class Main {
	
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		Database.initialize();
		
		logger.info("[DEMOJPA] Loading EntityManager, persistence unit...");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("punit_demojpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        logger.info("[DEMOJPA] Creating domain objects...");
        Client client01 = new Client();
        client01.setName("John Doe");
        Client client02 = new Client();
        client02.setName("Jane Doe");
        
        Order order01 = new Order();
        order01.setPrice(100);
        Order order02 = new Order();
        order02.setPrice(200);
        Order order03 = new Order();
        order03.setPrice(50);
        Order order04 = new Order();
        order04.setPrice(310);
        Order order05 = new Order();
        order05.setPrice(420);

        Item item01 = new Item();
        item01.setDescription("Item number 1");
        Item item02 = new Item();
        item02.setDescription("Item number 2");
        Item item03 = new Item();
        item03.setDescription("Item number 3");
        Item item04 = new Item();
        item04.setDescription("Item number 4");
        Item item05 = new Item();
        item05.setDescription("Item number 5");
        Item item06 = new Item();
        item06.setDescription("Item number 6");

        order01.addItem(item01);
        order01.addItem(item02);
        order01.addItem(item03);
        
        order02.addItem(item06);
       
        order03.addItem(item04);
        order03.addItem(item05);
        order03.addItem(item06);
        
        client01.addOrder(order01);
        client01.addOrder(order02);

        client02.addOrder(order03);
        client02.addOrder(order04);
        client02.addOrder(order05);

        logger.info("[DEMOJPA] Begin transaction...");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
        	logger.info("[DEMOJPA] Persist entities...");
        	entityManager.persist(client01);
        	entityManager.persist(client02);
        	transaction.commit();
        	
        	logger.info("-----------------------------------------------------------");
        	logger.info("[DEMOJPA] Domain data:");
        	printDomainObjects(client01);
        	printDomainObjects(client02);
        	logger.info("-----------------------------------------------------------");
        	logger.info("[DEMOJPA] Find objects using EntityManager:");
        	findObjets(entityManager);
        	logger.info("-----------------------------------------------------------");
        	logger.info("[DEMOJPA] Query Data using SQL with JDBC:");
        	logger.info("   Clients table:");
        	queryDataSQLwithJDBC("SELECT * FROM clients");
        	logger.info("   Orders table:");
        	queryDataSQLwithJDBC("SELECT * FROM orders");
        	logger.info("   Items table:");
        	queryDataSQLwithJDBC("SELECT * FROM items");       
        	logger.info("   Orders_Items table:");
        	queryDataSQLwithJDBC("SELECT * FROM orders_items"); 
        	logger.info("-----------------------------------------------------------");
        	logger.info("[DEMOJPA] Query Data using NativeQuery");
        	queryNativeQuery(entityManager);
        	logger.info("-----------------------------------------------------------");
        	logger.info("[DEMOJPA] Query Data using JPQL:");
        	queryDataJPQL(entityManager);
        	logger.info("-----------------------------------------------------------");
        	logger.info("[DEMOJPA] Query Data with a NamedQuery using JPQL:");
        	queryNamedQueryJPQL(entityManager);
        	logger.info("-----------------------------------------------------------");
        	logger.info("[DEMOJPA] Query Data with a NamedQuery using SQL native:");
        	queryNamedQuerySQL(entityManager);
        	logger.info("-----------------------------------------------------------");
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	transaction.rollback();
        } finally {
        	entityManager.close();
        	entityManagerFactory.close();
        }
	}
	
	private static void printDomainObjects(Client client) {
        logger.info(client.getId() + " " + client.getName() + " -> ");
        for (Order order : client.getOrders()) {
        	logger.info(" Order " + order.getId() + " " + order.getPrice());
        	for (Item item : order.getItems()) {
        		logger.info("         " + item.getId() + " \"" + item.getDescription() + "\"");
        	}
         }
	}
	
	private static void findObjets(EntityManager entityManager) {
		Order order = entityManager.find(Order.class, 2L);
       	logger.info(order.toString());
        for (Item item : order.getItems()) {
        	logger.info("\t" + item.toString());
        }
        logger.info("\t" + order.getClient().toString());
	}
	
	private static void queryDataSQLwithJDBC(String sqlQuery) {
		try {
			Connection connection = new DbConnection().getConnection();
	        
			Statement stm = connection.createStatement();
			boolean foundResults = stm.execute(sqlQuery);
	        
	        if (foundResults) {
	             ResultSet rs = stm.getResultSet();
	             if (rs != null) 
	            	 displayResults(rs);
	        } else {
	        	connection.close();
	        }
	    	connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	private static void displayResults(ResultSet set) throws SQLException {
        ResultSetMetaData metaData = set.getMetaData();
        int columns = metaData.getColumnCount();
        String text = "";

        while (set.next()) {
            for (int i=1; i <= columns; ++i) {
                text += "   " + metaData.getColumnName(i) + ":\t";
                text += set.getString(i);
                text += "\t";
            }
            text += "\n";
        }
        System.out.println(text);
    }
    
	@SuppressWarnings("unchecked")
	private static void queryNativeQuery(EntityManager entityManager) {
		//Si el resultado de la select coincide con el de la entidad se puede mapear directamente indicando el nombre de la misma
    	Query query = entityManager.createNativeQuery("SELECT * FROM items WHERE id < 4", Item.class);
    	List<Item> items = query.getResultList();
        for (Item item : items) {
        	logger.info(item.toString());
        }	
        
    	query = entityManager.createNativeQuery("SELECT name FROM clients");
    	List<String> clients = query.getResultList();
        for (String client : clients) {
        	logger.info("   NAME: " + client);
        }	
	}
	
	private static void queryDataJPQL(EntityManager entityManager) {
    	Query query = entityManager.createQuery("Select c from Client c where c.name = :name");
    	query.setParameter("name", "Jane Doe");
    	Client client = (Client) query.getSingleResult();
    	String text = "ID: " + client.getId() + "     NAME: " + client.getName();
    	logger.info(text);
        for (Order order : client.getOrders()) {
        	text = "   ID: " + order.getId() + "    PRICE: " + order.getPrice();
        	logger.info(text);
        }
    }
    
    @SuppressWarnings("unchecked")
    private static void queryNamedQueryJPQL(EntityManager entityManager) {
    	Query query = entityManager.createNamedQuery(Order.FIND_HIGH_PRICES);
    	List<Order> orders = query.getResultList();
        for (Order order : orders) {
        	String text = "   ID: " + order.getId() + "    PRICE: " + order.getPrice();
        	logger.info(text);
        }
    }
    
    @SuppressWarnings("unchecked")
    private static void queryNamedQuerySQL(EntityManager entityManager) {
    	Query query = entityManager.createNamedQuery(Order.FIND_LOW_PRICES);
    	List<Order> orders = query.getResultList();
        for (Order order : orders) {
        	String text = "   ID: " + order.getId() + "    PRICE: " + order.getPrice();
        	logger.info(text);
        }
    }
}