package dao;

import java.util.List;

import model.entities.Servico;

public interface ServicoDao {

	void insert(Servico servico);
	
	void update(Servico servicoNew, Servico servicoOld);
	
	void deleteById(Servico servicoOld);
	
	Servico findById(Integer id);
	
	List<Servico> findAll();
	
}
