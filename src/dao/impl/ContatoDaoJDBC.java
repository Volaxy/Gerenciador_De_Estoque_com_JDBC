package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ContatoDao;
import dao.PessoaDao;
import dao.TipoDao;
import db.DB;
import db.DbException;
import model.entities.Contato;
import model.entities.Pessoa;
import model.entities.Tipo;
import model.factories.DaoFactory;

public class ContatoDaoJDBC implements ContatoDao {

	private Connection conn;
	
	public ContatoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Contato contato) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into contato (descricao, id_tipo, id_pessoa) values "
				+	"(?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, contato.getDescricao());
			ps.setInt(2, contato.getTipo().getId());
			ps.setInt(3, contato.getPessoa().getId());
			
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
	public void update(Contato contatoNew, Contato contatoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update contato set descricao = ?, id_tipo = ?, id_pessoa = ? where "
				+ 	"id = ? and descricao = ? and id_tipo = ? and id_pessoa = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, contatoNew.getDescricao());
			ps.setInt(2, contatoNew.getTipo().getId());
			ps.setInt(3, contatoNew.getPessoa().getId());
			
			ps.setInt(4, contatoOld.getId());
			ps.setString(5, contatoOld.getDescricao());
			ps.setInt(6, contatoOld.getTipo().getId());
			ps.setInt(7, contatoOld.getPessoa().getId());
			
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
	public void deleteById(Contato contatoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from contato where "
				+ 	"id = ? and descricao = ? and id_tipo = ? and id_pessoa = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, contatoOld.getId());
			ps.setString(2, contatoOld.getDescricao());
			ps.setInt(3, contatoOld.getTipo().getId());
			ps.setInt(4, contatoOld.getPessoa().getId());
			
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
	public Contato findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao, id_tipo, id_pessoa from contato where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String descricao = rs.getString(2);
				
				Tipo tipo = createTipo(rs.getInt(3));
				Pessoa pessoa = createPessoa(rs.getInt(4));
				
				Contato contato = new Contato(id, descricao, tipo, pessoa);
				
				return contato;
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
	public List<Contato> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao, id_tipo, id_pessoa from contato;"
			);
			
			rs = ps.executeQuery();
			
			List<Contato> contatos = new ArrayList<Contato>();
			while(rs.next()) {
				int id = rs.getInt(1);
				String descricao = rs.getString(2);
				
				Map<Integer, Tipo> tipos = new HashMap<Integer, Tipo>();
				Map<Integer, Pessoa> pessoas = new HashMap<Integer, Pessoa>();
				
				int id_tipo = rs.getInt(3);
				int id_pessoa = rs.getInt(4);
				if(!tipos.containsKey(id_tipo)) {
					tipos.put(id_tipo, createTipo(id_tipo));
				}
				if(!pessoas.containsKey(id_pessoa)) {
					pessoas.put(id_pessoa, createPessoa(id_pessoa));
				}
				
				Tipo tipo = tipos.get(id_tipo);
				Pessoa pessoa = pessoas.get(id_pessoa);
				
				contatos.add(new Contato(id, descricao, tipo, pessoa));
				
				return contatos;
			}
			
			return contatos;
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

	private Tipo createTipo(int id) {
		TipoDao tipoDao = DaoFactory.createTipoDao();
		
		return tipoDao.findById(id);
	}

}
