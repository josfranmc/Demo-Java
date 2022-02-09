package org.josfranmc.demo.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;


public abstract class GenericDaoJdbc<T, ID extends Serializable> implements IGenericDao<T, ID> {

	protected Class<T> entityClass;

	protected Connection connection;

	@SuppressWarnings("unchecked")
	public GenericDaoJdbc(Connection connection) {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // [1]
		this.connection = connection;
	}
	
	@Override
	public T createElement() {
		T element = null;
		try {
			element = this.entityClass.getDeclaredConstructor().newInstance();
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
		}
		return element;
	}
}