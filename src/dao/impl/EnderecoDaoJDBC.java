package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.EnderecoDao;
import db.DB;
import db.DbException;
import model.entities.Endereco;

public class EnderecoDaoJDBC implements EnderecoDao {

	private Connection conn;
	
	public EnderecoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Endereco endereco) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into endereco (logradouro, numero, bairro, rua) values "
				+	"(?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, endereco.getLogradouro());
			ps.setInt(2, endereco.getNumero());
			ps.setString(3, endereco.getBairro());
			ps.setString(4, endereco.getRua());
			
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
	public void update(Endereco enderecoNew, Endereco enderecoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update endereco set logradouro = ?, numero = ?, bairro = ?, rua = ? "
				+ 	"where id = ? and logradouro = ? and numero = ? and bairro = ? and rua = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, enderecoNew.getLogradouro());
			ps.setInt(2, enderecoNew.getNumero());
			ps.setString(3, enderecoNew.getBairro());
			ps.setString(4, enderecoNew.getRua());
			
			ps.setInt(5, enderecoOld.getId());
			ps.setString(6, enderecoOld.getLogradouro());
			ps.setInt(7, enderecoOld.getNumero());
			ps.setString(8, enderecoOld.getBairro());
			ps.setString(9, enderecoOld.getRua());
			
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
	public void deleteById(Endereco enderecoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from endereco "
				+ 	"where id = ? and logradouro = ? and numero = ? and bairro = ? and rua = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, enderecoOld.getId());
			ps.setString(2, enderecoOld.getLogradouro());
			ps.setInt(3, enderecoOld.getNumero());
			ps.setString(4, enderecoOld.getBairro());
			ps.setString(5, enderecoOld.getRua());
			
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
	public Endereco findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, logradouro, numero, bairro, rua from endereco where id = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String logradouro = rs.getString(2);
				int numero = rs.getInt(3);
				String bairro = rs.getString(4);
				String rua = rs.getString(5);
				
				Endereco endereco = new Endereco(id, logradouro, numero, bairro, rua);
				
				return endereco;
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
	public List<Endereco> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					
					"select id, logradouro, numero, bairro, rua from endereco;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			
			rs = ps.executeQuery();
			
			List<Endereco> enderecos = new ArrayList<Endereco>();
			while(rs.next()) {
				int id = rs.getInt(1);
				String logradouro = rs.getString(2);
				int numero = rs.getInt(3);
				String bairro = rs.getString(4);
				String rua = rs.getString(5);
				
				Endereco endereco = new Endereco(id, logradouro, numero, bairro, rua);
				
				enderecos.add(endereco);
			}
			
			return enderecos;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}

}
