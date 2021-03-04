package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.TipoDao;
import db.DB;
import db.DbException;
import model.entities.Tipo;

public class TipoDaoJDBC implements TipoDao {

	private Connection conn;
	
	public TipoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Tipo tipo) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into tipo (descricao) values "
				+	"(?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, tipo.getDescricao());
			
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
	public void update(Tipo tipoNew, Tipo tipoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update tipo set descricao = ? where id = ? and descricao = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, tipoNew.getDescricao());
			ps.setInt(2, tipoOld.getId());
			ps.setString(3, tipoOld.getDescricao());
			
			int rows = ps.executeUpdate();
			
			if(rows == 0) {
				throw new DbException("O dado que você deseja alterar já foi alterado ou não existe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Tipo tipoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from tipo where id = ? and descricao = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, tipoOld.getId());
			ps.setString(2, tipoOld.getDescricao());
			
			int rows = ps.executeUpdate();
			
			if(rows == 0) {
				throw new DbException("Nenhum dado deletado pois o id não existe ou o dado foi alterado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public Tipo findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao from tipo where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String descricao = rs.getString(2);
				
				Tipo tipo = new Tipo(id, descricao);
				
				return tipo;
			}
			
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}

	@Override
	public List<Tipo> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao from tipo;"
			);
			
			rs = ps.executeQuery();
			
			List<Tipo> tipos = new ArrayList<Tipo>();
			while(rs.next()) {
				int id = rs.getInt(1);
				String descricao = rs.getString(2);
				
				Tipo tipo = new Tipo(id, descricao);
				
				tipos.add(tipo);
			}
			
			return tipos;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}

}
