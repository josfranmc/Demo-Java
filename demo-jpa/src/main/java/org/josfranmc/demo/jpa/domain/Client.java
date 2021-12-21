package org.josfranmc.demo.jpa.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=70)
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
	@OrderBy("price desc")
	private List<Order> orders;


	public Client() {
		this.orders = new ArrayList<Order>();
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
	
	public List<Order> getOrders() { 
		return Collections.unmodifiableList(orders);
	}

	public void addOrder(Order order) { 
		order.setClient(this); 
	}
	
	public void removeOrder(Order order) { 
		order.setClient(null); 
	}

	public void internalAddOrder(Order order) {
		this.orders.add(order); 
	}
	
	public void internalRemoveOrder(Order order) { 
		this.orders.remove(order); 
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