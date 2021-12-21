package org.josfranmc.demo.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.josfranmc.demo.jpa.domain.Client;
import org.josfranmc.demo.jpa.domain.Item;
import org.josfranmc.demo.jpa.domain.Order;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataTest {

	private Client client01;
	private Client client02;
	private Order order01;
	private Order order02;    
	private Order order03;
	private Order order04;
	private Order order05;
	private Item item01;
	private Item item02;
	private Item item03;
	private Item item04;
	private Item item05;
	private Item item06;
	
	private static EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;


	@BeforeClass
	public static void setUpClass() {
		Database.initialize();
		entityManagerFactory = Persistence.createEntityManagerFactory("punit_demojpa");
	}
	
	@Before
	public void setUp() {

		entityManager = entityManagerFactory.createEntityManager();
		
        client01 = new Client();
        client01.setName("John Doe");
        
        client02 = new Client();
        client02.setName("Jane Doe");
        
        order01 = new Order();
        order01.setPrice(100);
        
        order02 = new Order();
        order02.setPrice(200);
        
        order03 = new Order();
        order03.setPrice(50);
        
        order04 = new Order();
        order04.setPrice(310);
        
        order05 = new Order();
        order05.setPrice(420);

        client01.addOrder(order01);
        client01.addOrder(order02);
        
        client02.addOrder(order03);
        client02.addOrder(order04);
        client02.addOrder(order05);
        
        item01 = new Item();
        item01.setDescription("Item number 1");
        item02 = new Item();
        item02.setDescription("Item number 2");
        item03 = new Item();
        item03.setDescription("Item number 3");
        item04 = new Item();
        item04.setDescription("Item number 4");
        item05 = new Item();
        item05.setDescription("Item number 5");
        item06 = new Item();
        item06.setDescription("Item number 6");
        
        order01.addItem(item01);
        order01.addItem(item02);
        order01.addItem(item03);
        
        order02.addItem(item06);
       
        order03.addItem(item04);
        order03.addItem(item05);
        order03.addItem(item06);
		/*
        EntityTransaction transaction = entityManager.getTransaction();
        
        transaction.begin();
       	entityManager.persist(client01);
       	entityManager.persist(client02);
       	transaction.commit();*/
	}
	
	@Test
	public void test01_Check_Clients_Orders() {
		List<Order> orders_in_client01 = client01.getOrders();
		
		assertNotNull(orders_in_client01);
		assertEquals(2, orders_in_client01.size());
		assertEquals(100, orders_in_client01.get(0).getPrice());
		
		assertEquals("Jane Doe", order03.getClient().getName());
	}		
	
	@Test
	public void test02_Check_Orders_Items() {
		List<Item> items_in_order01 = order01.getItems();
		
		assertNotNull(items_in_order01);
		assertEquals(3, items_in_order01.size());
		assertEquals("Item number 1", items_in_order01.get(0).getDescription());
		
		assertEquals(2, item06.getOrders().size());
	}
	
	@Test
	public void test03_Persist_Entities() {
		System.out.println("[DEMOJPA] Persist Entities");
        EntityTransaction transaction = entityManager.getTransaction();
        
       	assertFalse(entityManager.contains(client01));
       	assertFalse(entityManager.contains(client02));
        
        transaction.begin();
       	entityManager.persist(client01);
       	entityManager.persist(client02);
       	transaction.commit();		
		
       	assertTrue(entityManager.contains(client01));
       	assertTrue(entityManager.contains(client02));
	}
	
	@Test
	public void test04_QueryDataSQL() {
		System.out.println("[DEMOJPA] Query Database Data using SQL:");
		try {
			Connection connection = new DbConnection().getConnection();
	        
			Statement stm = connection.createStatement();
			boolean foundResults = stm.execute("SELECT * FROM clients WHERE name = 'John Doe'");
			
			assertTrue(foundResults);
	    	connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test05_QueryDataJPQL() {
		System.out.println("[DEMOJPA] Query Database Data using JPQL:");
    	Query query = entityManager.createQuery("Select c from Client c where c.name = :name");
    	query.setParameter("name", "Jane Doe");
    	Client client = (Client) query.getSingleResult();
    	
    	assertNotNull(client);
    	assertEquals("Jane Doe", client.getName());
    	System.out.println("2");
    	assertNotNull(client.getOrders());
    }
	
	@Test
	@SuppressWarnings("unchecked")
	public void test06_QueryNamedQueryJPQL() {
		System.out.println("[DEMOJPA] Query Data with a NamedQuery using JPQL:");
    	Query query = entityManager.createNamedQuery(Order.FIND_HIGH_PRICES);
    	List<Order> orders = query.getResultList();
    	assertNotNull(orders);
    }	
	
	@Test
	@SuppressWarnings("unchecked")
	public void test06_QueryNamedQuerySQL() {
		System.out.println("[DEMOJPA] Query Data with a NamedQuery using SQL native:");
		
    	Query query = entityManager.createNamedQuery(Order.FIND_LOW_PRICES);
    	List<Order> orders = query.getResultList();
    	assertNotNull(orders);
    }	
}
