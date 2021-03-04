package dao;

import java.util.List;

import model.entities.Venda;

public interface VendaDao {

	void insert(Venda venda);
	
	void update(Venda vendaNew, Venda vendaOld);
	
	void deleteById(Venda vendaOld);
	
	Venda findById(Integer id);
	
	List<Venda> findAll();
	
}
