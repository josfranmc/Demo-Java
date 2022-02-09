package org.josfranmc.demo.dao;

import java.io.Serializable;
import java.util.List;
//import java.util.Optional;

/**
 * Interface with basic operations to manage database tables (CRUD operations).
 * @author josfranmc
 * @version 1.0
 */
public interface IGenericDao<T, ID extends Serializable> {

	T createElement();
	
	Long save(T element);
	
	void update(T element);
	
	void deleteElement(T element);
	
	T getElementById(ID id);
	
	List<T> getAllElements();
}