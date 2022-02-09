package org.josfranmc.demo.dao;

import java.sql.Connection;
import java.util.List;

import org.josfranmc.demo.domain.Item;

public class ItemDaoJdbc extends GenericDaoJdbc<Item, Long> implements IItemDao {

	public ItemDaoJdbc(Connection connection) {
		super(connection);
	}

	@Override
	public Long save(Item element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Item element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteElement(Item element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item getElementById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getAllElements() {
		// TODO Auto-generated method stub
		return null;
	}
}