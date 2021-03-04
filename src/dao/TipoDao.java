package dao;

import java.util.List;

import model.entities.Tipo;

public interface TipoDao {

	void insert(Tipo tipo);
	
	void update(Tipo tipoNew, Tipo tipoOld);
	
	void deleteById(Tipo tipoOld);
	
	Tipo findById(Integer id);
	
	List<Tipo> findAll();
	
}
