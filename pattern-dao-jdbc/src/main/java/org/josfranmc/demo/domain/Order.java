package org.josfranmc.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

	public static final String FIND_HIGH_PRICES = "Order.findHighPrices";
	public static final String FIND_LOW_PRICES = "Order.findLowPrices";

	private Long id;

	private long price;

	private Client client;

	private List<Item> itemsOrdered;

	
	public Order () {
		this.itemsOrdered = new ArrayList<Item>();
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
		this.client = client;
	}
	
	public List<Item> getItemsOrdered() {
		return itemsOrdered;
	}
	
	public void setItemsOrdered(List<Item> itemsOrdered) {
		this.itemsOrdered = itemsOrdered;
	}

	public void addItemOrdered(Item item) {
		this.itemsOrdered.add(item);
	}
	
	public void removeItemOrdered(Item item) {
		this.itemsOrdered.remove(item);
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