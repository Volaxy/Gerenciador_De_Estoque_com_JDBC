package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ClienteDao;
import dao.FornecedorDao;
import dao.ProdutoDao;
import dao.VendaDao;
import db.DB;
import db.DbException;
import model.entities.Cliente;
import model.entities.Fornecedor;
import model.entities.Produto;
import model.entities.Venda;
import model.factories.DaoFactory;

public class VendaDaoJDBC implements VendaDao {

	private Connection conn;
	
	public VendaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Venda venda) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"insert into venda (data, total, id_cliente, id_produto, id_fornecedor) values "
				+ 	"(?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setDate(1, new Date(venda.getData().getTime()));
			ps.setDouble(2, venda.getTotal());
			ps.setInt(3, venda.getCliente().getId());
			ps.setInt(4, venda.getProduto().getId());
			ps.setInt(5, venda.getFornecedor().getId());
			
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
	public void update(Venda vendaNew, Venda vendaOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"update venda set data = ?, total = ?, id_cliente = ?, id_produto = ?, id_fornecedor = ? where "
				+ 	"id = ? and data = ? and total = ? and id_cliente = ? and id_produto = ? and id_fornecedor = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setDate(1, new Date(vendaNew.getData().getTime()));
			ps.setDouble(2, vendaNew.getTotal());
			ps.setInt(3, vendaNew.getCliente().getId());
			ps.setInt(4, vendaNew.getProduto().getId());
			ps.setInt(5, vendaNew.getFornecedor().getId());
			
			ps.setInt(6, vendaOld.getId());
			ps.setDate(7, new Date(vendaOld.getData().getTime()));
			ps.setDouble(8, vendaOld.getTotal());
			ps.setInt(9, vendaOld.getCliente().getId());
			ps.setInt(10, vendaOld.getProduto().getId());
			ps.setInt(11, vendaOld.getFornecedor().getId());
			
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
	public void deleteById(Venda vendaOld) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
					"delete from venda where "
				+ 	"id = ? and data = ? and total = ? and id_cliente = ? and id_produto = ? and id_fornecedor = ?;", PreparedStatement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, vendaOld.getId());
			ps.setDate(2, new Date(vendaOld.getData().getTime()));
			ps.setDouble(3, vendaOld.getTotal());
			ps.setInt(4, vendaOld.getCliente().getId());
			ps.setInt(5, vendaOld.getProduto().getId());
			ps.setInt(6, vendaOld.getFornecedor().getId());
			
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
	public Venda findById(Integer idSearch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, data, total, id_cliente, id_produto, id_fornecedor from venda where id = ?;"
			);
			ps.setInt(1, idSearch);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				Date data = rs.getDate(2);
				double total = rs.getDouble(3);
				int id_cliente = rs.getInt(4);
				int id_produto = rs.getInt(5);
				int id_fornecedor = rs.getInt(6);
				
				Cliente cliente = createCliente(id_cliente);
				Produto produto = createProduto(id_produto);
				Fornecedor fornecedor = createFornecedor(id_fornecedor);
				
				Venda venda = new Venda(id, data, total, cliente, produto, fornecedor);
				
				return venda;
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
	public List<Venda> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"select id, descricao from venda;"
			);
			
			rs = ps.executeQuery();
			
			List<Venda> vendas = new ArrayList<Venda>();
			while(rs.next()) {
				int id = rs.getInt(1);
				Date data = rs.getDate(2);
				double total = rs.getDouble(3);
				int id_cliente = rs.getInt(4);
				int id_produto = rs.getInt(5);
				int id_fornecedor = rs.getInt(6);
				
				Map<Integer, Cliente> clientes = new HashMap<Integer, Cliente>();
				Map<Integer, Produto> produtos = new HashMap<Integer, Produto>();
				Map<Integer, Fornecedor> fornecedores = new HashMap<Integer, Fornecedor>();
				
				if(!clientes.containsKey(id_cliente)) {
					clientes.put(id_cliente, createCliente(id_cliente));
				}
				
				if(!produtos.containsKey(id_produto)) {
					produtos.put(id_produto, createProduto(id_produto));
				}
				
				if(!fornecedores.containsKey(id_fornecedor)) {
					fornecedores.put(id_fornecedor, createFornecedor(id_fornecedor));
				}
				
				Cliente cliente = clientes.get(id_cliente);
				Produto produto = produtos.get(id_produto);
				Fornecedor fornecedor = fornecedores.get(id_fornecedor);
				
				Venda venda = new Venda(id, data, total, cliente, produto, fornecedor);

				vendas.add(venda);
			}
			
			return vendas;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	}
	
	private Fornecedor createFornecedor(int id) {
		FornecedorDao fornecedorDao = DaoFactory.createFornecedorDao();
		
		return fornecedorDao.findById(id);
	}

	private Produto createProduto(int id) {
		ProdutoDao produtoDao = DaoFactory.createProdutoDao();
		
		return produtoDao.findById(id);
	}

	private Cliente createCliente(int id) {
		ClienteDao clienteDao = DaoFactory.createClienteDao();
		
		return clienteDao.findById(id);
	}

}
