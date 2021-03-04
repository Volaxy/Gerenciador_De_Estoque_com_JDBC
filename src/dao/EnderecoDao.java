package dao;

import java.util.List;

import model.entities.Endereco;

public interface EnderecoDao {

	void insert(Endereco endereco);
	
	void update(Endereco enderecoNew, Endereco enderecoOld);
	
	void deleteById(Endereco enderecoOld);
	
	Endereco findById(Integer id);
	
	List<Endereco> findAll();
	
}
