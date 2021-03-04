package dao;

import java.util.List;

import model.entities.Oferta;

public interface OfertaDao {

	void insert(Oferta oferta);
	
	void update(Oferta ofertaNew, Oferta ofertaOld);
	
	void deleteById(Oferta ofertaOld);
	
	Oferta findById(Integer id);
	
	List<Oferta> findAll();
	
}
