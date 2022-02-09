package org.josfranmc.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Client {
	
	private Long id;
	
	private String name;

	private List<Order> orders;

	public Client() {
		this.orders = new ArrayList<Order>();
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getName()	{
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId()	{
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
		order.setClient(this);
	}

	public void removeOrder(Order order) {
		this.orders.remove(order);
		order.setClient(null);
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}