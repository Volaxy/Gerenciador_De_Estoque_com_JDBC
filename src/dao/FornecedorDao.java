package dao;

import java.util.List;

import model.entities.Fornecedor;

public interface FornecedorDao {

	void insert(Fornecedor fornecedor);
	
	void update(Fornecedor fornecedorNew, Fornecedor fornecedorOld);
	
	void deleteById(Fornecedor fornecedorOld);
	
	Fornecedor findById(Integer id);
	
	List<Fornecedor> findAll();
	
}
