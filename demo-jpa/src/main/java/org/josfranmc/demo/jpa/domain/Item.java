package org.josfranmc.demo.jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String description;

	@ManyToMany(mappedBy = "items")
	private List<Order> orders;

	
	public Item () {
		this.orders = new ArrayList<Order>();
	}
	
	public Long getId()	{
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription()	{
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Order> getOrders() {
		return orders;
	}
	
	public void addOrder(Order order) {
		if (!this.orders.contains(order)) {
			this.orders.add(order);
			order.addItem(this);
		}
	}
	
	public void removeOrder(Order order) {
		if (this.orders.contains(order)) {
			this.orders.remove(order);
			order.removeItem(this);
		}
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", description=" + description + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}