package dao;

import java.util.List;

import model.entities.Pessoa;

public interface PessoaDao {

	void insert(Pessoa pessoa);
	
	void update(Pessoa pessoaNew, Pessoa pessoaOld);
	
	void deleteById(Pessoa pessoaOld);
	
	Pessoa findById(Integer id);
	
	List<Pessoa> findAll();
	
}
