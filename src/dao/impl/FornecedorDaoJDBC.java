package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.FornecedorDao;
import dao.PessoaDao;
import db.DB;
import db.DbException;
import model.entities.Fornecedor;
import model.entities.Pessoa;
import model.factories.DaoFactory;

public class FornecedorDaoJDBC implements FornecedorDao {

	private Connection conn;
	
	public FornecedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Fornecedor fornecedor) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into fornecedor (id_pessoa) values "
				+	"(?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, fornecedor.getPessoa().getId());
			
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
	public void update(Fornecedor fornecedorNew, Fornecedor fornecedorOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update fornecedor set id_pessoa = ? where id = ? and id_pessoa = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, fornecedorNew.getPessoa().getId());
			
			ps.setInt(2, fornecedorOld.getId());
			ps.setInt(3, fornecedorOld.getPessoa().getId());
			
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
	public void deleteById(Fornecedor fornecedorOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from fornecedor where id = ? and id_pessoa = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, fornecedorOld.getId());
			ps.setInt(2, fornecedorOld.getPessoa().getId());
			
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
	public Fornecedor findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, id_pessoa from fornecedor where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				
				Pessoa pessoa = createPessoa(rs.getInt(2));
				
				Fornecedor fornecedor = new Fornecedor(id, pessoa);
				
				return fornecedor;
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
	public List<Fornecedor> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao from fornecedor;"
			);
			
			rs = ps.executeQuery();
			
			List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
			while(rs.next()) {
				int id = rs.getInt(1);
				
				Map<Integer, Pessoa> pessoas = new HashMap<Integer, Pessoa>();
				
				int id_pessoa = rs.getInt(2);
				if(!pessoas.containsKey(id_pessoa)) {
					pessoas.put(id_pessoa, createPessoa(id_pessoa));
				}
				
				Fornecedor fornecedor = new Fornecedor(id, pessoas.get(id_pessoa));
				
				fornecedores.add(fornecedor);
			}
			
			return fornecedores;
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
