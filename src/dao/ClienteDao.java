package dao;

import java.util.List;

import model.entities.Cliente;

public interface ClienteDao {

	void insert(Cliente cliente);
	
	void update(Cliente clienteNew, Cliente clienteOld);
	
	void deleteById(Cliente clienteOld);
	
	Cliente findById(Integer id);
	
	List<Cliente> findAll();
	
}
