package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.CategoriaDao;
import dao.ProdutoDao;
import db.DB;
import db.DbException;
import model.entities.Categoria;
import model.entities.Produto;
import model.factories.DaoFactory;

public class ProdutoDaoJDBC implements ProdutoDao {

	private Connection conn;
	
	public ProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Produto produto) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into produto (nome, preco, quantidade, custo, id_categoria) values "
				+	"(?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, produto.getNome());
			ps.setDouble(2, produto.getPreco());
			ps.setInt(3, produto.getQuantidade());
			ps.setDouble(4, produto.getCusto());
			ps.setInt(5, produto.getCategoria().getId());
			
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
	public void update(Produto produtoNew, Produto produtoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update produto set nome = ?, preco = ?, quantidade = ?, custo = ?, id_categoria = ? where "
				+ 	"id = ? and nome = ? and preco = ? and quantidade = ? and custo = ? and id_categoria = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, produtoNew.getNome());
			ps.setDouble(2, produtoNew.getPreco());
			ps.setInt(3, produtoNew.getQuantidade());
			ps.setDouble(4, produtoNew.getCusto());
			ps.setInt(5, produtoNew.getCategoria().getId());
			
			ps.setInt(6, produtoOld.getId());
			ps.setString(7, produtoOld.getNome());
			ps.setDouble(8, produtoOld.getPreco());
			ps.setInt(9, produtoOld.getQuantidade());
			ps.setDouble(10, produtoOld.getCusto());
			ps.setInt(11, produtoOld.getCategoria().getId());
			
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
	public void deleteById(Produto produtoOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from produto where "
				+ 	"id = ? and nome = ? and preco = ? and quantidade = ? and custo = ? and id_categoria = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, produtoOld.getId());
			ps.setString(2, produtoOld.getNome());
			ps.setDouble(3, produtoOld.getPreco());
			ps.setInt(4, produtoOld.getQuantidade());
			ps.setDouble(5, produtoOld.getCusto());
			ps.setInt(6, produtoOld.getCategoria().getId());
			
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
	public Produto findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, nome, preco, quantidade, custo, id_categoria from produto "
				+ 	"where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				double preco = rs.getDouble(3);
				int quantidade = rs.getInt(4);
				double custo = rs.getDouble(5);
				
				Categoria categoria = createCategoria(rs.getInt(6));
				
				Produto produto = new Produto(id, nome, preco, quantidade, custo, categoria);
				
				return produto;
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
	public List<Produto> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, nome, preco, quantidade, custo, id_categoria from produto;"
			);
			
			rs = ps.executeQuery();
			
			List<Produto> produtos = new ArrayList<Produto>();
			while(rs.next()) {
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				double preco = rs.getDouble(3);
				int quantidade = rs.getInt(4);
				double custo = rs.getDouble(5);
				
				Map<Integer, Categoria> categorias = new HashMap<Integer, Categoria>(); 
				
				int id_categoria = rs.getInt(6);
				if(!categorias.containsKey(id_categoria)) {
					categorias.put(id_categoria, createCategoria(id_categoria));
				}
				
				Categoria categoria = categorias.get(id_categoria);
				
				Produto produto = new Produto(id, nome, preco, quantidade, custo, categoria);
				
				produtos.add(produto);
			}
			
			return produtos;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}
	
	private Categoria createCategoria(int id) {
		CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
		
		return categoriaDao.findById(id);
	}

}
