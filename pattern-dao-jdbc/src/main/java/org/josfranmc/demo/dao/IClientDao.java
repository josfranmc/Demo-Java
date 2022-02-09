package org.josfranmc.demo.dao;

import org.josfranmc.demo.domain.Client;

public interface IClientDao extends IGenericDao<Client, Long> {

	public Client findByName(String name);
}