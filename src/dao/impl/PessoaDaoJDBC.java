package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.EnderecoDao;
import dao.PessoaDao;
import db.DB;
import db.DbException;
import model.entities.Endereco;
import model.entities.Pessoa;
import model.factories.DaoFactory;

public class PessoaDaoJDBC implements PessoaDao {

	private Connection conn;
	
	public PessoaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Pessoa pessoa) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into pessoa (nome, nomeJuridico, id_endereco) values "
				+ 	"(?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, pessoa.getNome());
			ps.setString(2, pessoa.getNomeJuridico());
			ps.setInt(3, pessoa.getEndereco().getId());
			
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
	public void update(Pessoa pessoaNew, Pessoa pessoaOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update pessoa set nome = ?, nomeJuridico = ?, id_endereco = ? where "
				+ 	"id = ? and nome = ? and nomeJuridico is ? and id_endereco = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, pessoaNew.getNome());
			ps.setString(2, pessoaNew.getNomeJuridico());
			ps.setInt(3, pessoaNew.getEndereco().getId());
			
			ps.setInt(4, pessoaOld.getId());
			ps.setString(5, pessoaOld.getNome());
			ps.setString(6, pessoaOld.getNomeJuridico());
			ps.setInt(7, pessoaOld.getEndereco().getId());
			
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
	public void deleteById(Pessoa pessoaOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from pessoa where "
				+ 	"id = ? and nome = ? and nomeJuridico is ? and id_endereco = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, pessoaOld.getId());
			ps.setString(2, pessoaOld.getNome());
			ps.setString(3, pessoaOld.getNomeJuridico());
			ps.setInt(4, pessoaOld.getEndereco().getId());
			
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
	public Pessoa findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, nome, nomeJuridico, id_endereco from pessoa where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				String nomeJuridico = rs.getString(3);
				/*TODO Mudar a forma como se instância o "Tipo",
				 * aqui eu coloquei "cliente" por questão de sim-
				 * plicidade, mas ao decorrer do projeto será nes-
				 * cessério mudar esta forma de instânciação
				*/
				String tipo = "cliente";
				
				Endereco endereco = createEndereco(rs.getInt(4));
				
				Pessoa pessoa = new Pessoa(id, nome, nomeJuridico, tipo, endereco);
				
				return pessoa;
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
	public List<Pessoa> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, nome, nomeJuridico, id_endereco from pessoa;"
			);
			
			rs = ps.executeQuery();
			
			List<Pessoa> pessoas = new ArrayList<Pessoa>();
			while(rs.next()) {
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				String nomeJuridico = rs.getString(3);
				/*TODO Mudar a forma como se instância o "Tipo",
				 * aqui eu coloquei "cliente" por questão de sim-
				 * plicidade, mas ao decorrer do projeto será nes-
				 * cessério mudar esta forma de instânciação
				*/
				String tipo = "cliente";
				
				Map<Integer, Endereco> enderecos = new HashMap<Integer, Endereco>();
				
				int id_endereco = rs.getInt(4);
				if(!enderecos.containsKey(id_endereco)) {
					enderecos.put(id_endereco, createEndereco(id_endereco));
				}
				
				Endereco endereco = enderecos.get(id_endereco);
				
				Pessoa pessoa = new Pessoa(id, nome, nomeJuridico, tipo, endereco);
				
				pessoas.add(pessoa);
			}
			
			return pessoas;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}
	
	private Endereco createEndereco(int id) {
		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
		
		return enderecoDao.findById(id);
	}

}
