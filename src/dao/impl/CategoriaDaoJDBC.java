package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CategoriaDao;
import db.DB;
import db.DbException;
import model.entities.Categoria;

public class CategoriaDaoJDBC implements CategoriaDao {

	private Connection conn;
	
	public CategoriaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Categoria categoria) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into categoria (descricao) values "
				+ 	"(?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, categoria.getDescricao());
			
			int rows = ps.executeUpdate();
			
			if(rows == 0) {
				throw new DbException("Nenhum dado inserido");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Categoria categoriaNew, Categoria categoriaOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update categoria set descricao = ? "
				+ 	"where id = ? and descricao = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, categoriaNew.getDescricao());
			
			ps.setInt(2, categoriaOld.getId());
			ps.setString(3, categoriaOld.getDescricao());
			
			int rows = ps.executeUpdate();
			
			if(rows == 0) {
				throw new DbException("Nenhum dado atualizado pois os dados já foram atualizados ou o dado que você quer alterar não existe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Categoria categoriaOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from categoria "
				+ 	"where id = ? and descricao = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, categoriaOld.getId());
			ps.setString(2, categoriaOld.getDescricao());
			int rows = ps.executeUpdate();
			
			if(rows == 0) {
				throw new DbException("Nenhum dado deletado pois o dado foi alterado ou não existe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public Categoria findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao from categoria "
				+ 	"where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String descricao = rs.getString(2);
				
				Categoria categoria = new Categoria(id, descricao);
				
				return categoria;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}

	@Override
	public List<Categoria> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					
					"select id, descricao from categoria;"
			);
			
			rs = ps.executeQuery();
			
			List<Categoria> categorias = new ArrayList<Categoria>();
			while(rs.next()) {
				int id = rs.getInt(1);
				String descricao = rs.getString(2);
				
				Categoria categoria = new Categoria(id, descricao);
				
				categorias.add(categoria);
			}
			
			return categorias;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}

}
