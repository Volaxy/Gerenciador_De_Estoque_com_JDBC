package dao;

import java.util.List;

import model.entities.Categoria;

public interface CategoriaDao {

	void insert(Categoria categoria);
	
	void update(Categoria categoriaNew, Categoria categoriaOld);
	
	void deleteById(Categoria categoriaOld);
	
	Categoria findById(Integer id);
	
	List<Categoria> findAll();
	
}
