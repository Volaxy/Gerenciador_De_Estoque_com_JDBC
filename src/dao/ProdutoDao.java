package dao;

import java.util.List;

import model.entities.Produto;

public interface ProdutoDao {

	void insert(Produto produto);
	
	void update(Produto produtoNew, Produto produtoOld);
	
	void deleteById(Produto produtoOld);
	
	Produto findById(Integer id);
	
	List<Produto> findAll();
	
}
