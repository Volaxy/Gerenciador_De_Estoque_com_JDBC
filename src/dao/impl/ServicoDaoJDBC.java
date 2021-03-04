package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ServicoDao;
import db.DB;
import db.DbException;
import model.entities.Servico;

public class ServicoDaoJDBC implements ServicoDao {

	private Connection conn;
	
	public ServicoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Servico servico) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into servico (descricao) "
				+ 	"values (?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, servico.getDescricao());
			
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
	public void update(Servico servicoNew, Servico servicoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update servico set descricao = ? "
				+ 	"where id = ? and descricao = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, servicoNew.getDescricao());
			
			ps.setInt(2, servicoOld.getId());
			ps.setString(3, servicoOld.getDescricao());
			
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
	public void deleteById(Servico servicoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from servico "
				+ 	"where id = ? and descricao = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, servicoOld.getId());
			ps.setString(2, servicoOld.getDescricao());
			
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
	public Servico findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao from servico where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String descricao = rs.getString(2);
				
				Servico servico = new Servico(id, descricao);
				
				return servico;
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
	public List<Servico> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					
					"select id, descricao from servico;"
			);
			
			rs = ps.executeQuery();
			
			List<Servico> servicos = new ArrayList<Servico>();
			while(rs.next()) {
				int id = rs.getInt(1);
				String descricao = rs.getString(2);
				
				Servico servico = new Servico(id, descricao);
				
				servicos.add(servico);
			}
			
			return servicos;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}

}
