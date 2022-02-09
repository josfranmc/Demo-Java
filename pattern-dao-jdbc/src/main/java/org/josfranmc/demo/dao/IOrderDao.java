package org.josfranmc.demo.dao;

import org.josfranmc.demo.domain.Order;

public interface IOrderDao extends IGenericDao<Order, Long> {

	public Order findMaxPrice();
}