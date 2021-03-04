package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ClienteDao;
import dao.PessoaDao;
import db.DB;
import db.DbException;
import model.entities.Cliente;
import model.entities.Pessoa;
import model.factories.DaoFactory;

public class ClienteDaoJDBC implements ClienteDao {

	private Connection conn;
	
	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Cliente cliente) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into cliente (id_pessoa) values "
				+	"(?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, cliente.getPessoa().getId());
			
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
	public void update(Cliente clienteNew, Cliente clienteOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update cliente set id_pessoa = ? where id = ? and id_pessoa = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, clienteNew.getPessoa().getId());
			
			ps.setInt(2, clienteOld.getId());
			ps.setInt(3, clienteOld.getPessoa().getId());
			
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
	public void deleteById(Cliente clienteOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from cliente where id = ? and id_pessoa = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, clienteOld.getId());
			ps.setInt(2, clienteOld.getPessoa().getId());
			
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
	public Cliente findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, id_pessoa from cliente where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				
				Pessoa pessoa = createPessoa(rs.getInt(2));
				
				Cliente cliente = new Cliente(id, pessoa);
				
				return cliente;
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
	public List<Cliente> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao from cliente;"
			);
			
			rs = ps.executeQuery();
			
			List<Cliente> clientes = new ArrayList<Cliente>();
			while(rs.next()) {
				int id = rs.getInt(1);
				
				Map<Integer, Pessoa> pessoas = new HashMap<Integer, Pessoa>();
				
				int id_pessoa = rs.getInt(2);
				if(!pessoas.containsKey(id_pessoa)) {
					pessoas.put(id_pessoa, createPessoa(id_pessoa));
				}
				
				Cliente cliente = new Cliente(id, pessoas.get(id_pessoa));
				
				clientes.add(cliente);
			}
			
			return clientes;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}
	
	private Pessoa createPessoa(int id) {
		PessoaDao pessoaDao = DaoFactory.createPessoaDao();
		
		return pessoaDao.findById(id);
	}

}
