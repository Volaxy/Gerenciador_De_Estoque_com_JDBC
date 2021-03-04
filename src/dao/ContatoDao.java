package dao;

import java.util.List;

import model.entities.Contato;

public interface ContatoDao {

	void insert(Contato contato);
	
	void update(Contato contatoNew, Contato contatoOld);
	
	void deleteById(Contato contatoOld);
	
	Contato findById(Integer id);
	
	List<Contato> findAll();
	
}
