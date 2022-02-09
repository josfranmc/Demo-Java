package org.josfranmc.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.josfranmc.demo.domain.Client;

public class ClientDaoJdbc extends GenericDaoJdbc<Client, Long> implements IClientDao {

	public ClientDaoJdbc(Connection connection) {
		super(connection);
	}

	@Override
	public Long save(Client element) {
		Long generatedKey = null;
		try (PreparedStatement stm = connection.prepareStatement("INSERT INTO clients(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
			//stm.setLong(1, element.getId());
			stm.setString(1, element.getName());
			stm.executeUpdate();
			ResultSet generatedKeys = stm.getGeneratedKeys();
			if (generatedKeys.next()) {
				generatedKey = generatedKeys.getLong(1);
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		return generatedKey;
	}

	@Override
	public void update(Client element) {
		try (PreparedStatement stm = connection.prepareStatement("UPDATE clients SET name=? WHERE id=?")) {
			stm.setString(1, element.getName());
			stm.setLong(2, element.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void deleteElement(Client element) {
		try (PreparedStatement stm = connection.prepareStatement("DELETE FROM clients WHERE id=?")) {
			stm.setLong(1, element.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public Client getElementById(Long id) {
		Client client = null;
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM clients WHERE id=?")) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
            	client = getClientFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return client;
	}

	@Override
	public List<Client> getAllElements() {
		List<Client> clients = new ArrayList<Client>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM clients")) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
            	clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return clients;
	}
	
	@Override
	public Client findByName(String name) {
		Client client = null;
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM clients WHERE name=?")) {
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
            	client = getClientFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return client;
	}
	
	private Client getClientFromResultSet(ResultSet rs) {
		Client client = createElement();
		try {
			client.setId(rs.getLong(1));
			client.setName(rs.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}
}