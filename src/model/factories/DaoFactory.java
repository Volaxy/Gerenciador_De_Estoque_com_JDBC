package model.factories;

import dao.CategoriaDao;
import dao.ClienteDao;
import dao.EnderecoDao;
import dao.FornecedorDao;
import dao.PessoaDao;
import dao.ProdutoDao;
import dao.ServicoDao;
import dao.TipoDao;
import dao.impl.CategoriaDaoJDBC;
import dao.impl.ClienteDaoJDBC;
import dao.impl.EnderecoDaoJDBC;
import dao.impl.FornecedorDaoJDBC;
import dao.impl.PessoaDaoJDBC;
import dao.impl.ProdutoDaoJDBC;
import dao.impl.ServicoDaoJDBC;
import dao.impl.TipoDaoJDBC;
import db.DB;

public class DaoFactory {
	
	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoJDBC(DB.getConnection());
	}
	
	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
	public static EnderecoDao createEnderecoDao() {
		return new EnderecoDaoJDBC(DB.getConnection());
	}
	
	public static FornecedorDao createFornecedorDao() {
		return new FornecedorDaoJDBC(DB.getConnection());
	}
	
	public static PessoaDao createPessoaDao() {
		return new PessoaDaoJDBC(DB.getConnection());
	}
	
	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	
	public static ServicoDao createServicoDao() {
		return new ServicoDaoJDBC(DB.getConnection());
	}
	
	public static TipoDao createTipoDao() {
		return new TipoDaoJDBC(DB.getConnection());
	}
	
}
