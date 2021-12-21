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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@NamedQuery(name=Order.FIND_HIGH_PRICES, query="SELECT o FROM Order o WHERE o.price > 100")
@NamedNativeQuery(name=Order.FIND_LOW_PRICES, query="SELECT * FROM orders o WHERE o.price <= 100", resultClass = Order.class)
public class Order {

	public static final String FIND_HIGH_PRICES = "Order.findHighPrices";
	public static final String FIND_LOW_PRICES = "Order.findLowPrices";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private long price;
	
	@ManyToOne(optional=false, fetch = FetchType.LAZY) //by default FetchType.EAGER, bad for performance
	@JoinColumn(name = "client", nullable = false)
	private Client client;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "orders_items", 
			   joinColumns = @JoinColumn(name = "order_id", nullable = false), 
			   inverseJoinColumns = @JoinColumn(name = "item_id", nullable = false))
	private List<Item> items;

	
	public Order () {
		this.items = new ArrayList<Item>();
	}
	
	public Long getId()	{
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPrice()	{
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		if (this.client != null) { 
			this.client.internalRemoveOrder(this); 
		}
		this.client = client;
		if (client != null) {
			client.internalAddOrder(this); 
		}
	}
	
	public List<Item> getItems() {
		return Collections.unmodifiableList(items);
	}

	public void addItem(Item item) {
		if (!this.items.contains(item)) {
			this.items.add(item);
			item.addOrder(this);	
		}
	}
	
	public void removeItem(Item item) {
		item.removeOrder(this);
		if (this.items.contains(item)) {
			this.items.remove(item);
			item.removeOrder(this);	
		}		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
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
		Order other = (Order) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}