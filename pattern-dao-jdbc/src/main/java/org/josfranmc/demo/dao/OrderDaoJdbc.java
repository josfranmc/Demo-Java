package org.josfranmc.demo.dao;

import java.sql.Connection;
import java.util.List;

import org.josfranmc.demo.domain.Order;

public class OrderDaoJdbc extends GenericDaoJdbc<Order, Long> implements IOrderDao {

	public OrderDaoJdbc(Connection connection) {
		super(connection);
	}

	@Override
	public Long save(Order element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Order element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteElement(Order element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order getElementById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findMaxPrice() {
        return null;
	}
}