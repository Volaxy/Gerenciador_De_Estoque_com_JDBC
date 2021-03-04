package application;

import view.IndexFrame;

public class Program {

	public static void main(String[] args) {
		
		IndexFrame index = new IndexFrame();
		index.setVisible(true);
		
		//Declaração da (ENTIDADE)Dao
//		CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
//		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
//		PessoaDao pessoaDao = DaoFactory.createPessoaDao();
//		ProdutoDao produtoDao = DaoFactory.createProdutoDao();
//		ServicoDao servicoDao = DaoFactory.createServicoDao();
//		TipoDao tipoDao = DaoFactory.createTipoDao();
		
		//**********************************************************************//
		
		//Categoria
		
//		categoriaDao.insert(new Categoria(null, "Chocolates"));
//		categoriaDao.update(new Categoria(null, "Pizzas"), categoriaDao.findById(3));
//		categoriaDao.deleteById(categoriaDao.findById(4));
//		System.out.println(categoriaDao.findById(2));
//		
//		for (Categoria c : categoriaDao.findAll()) {
//			System.out.println(c);
//		}
		
		///////////////////////////////////////////////////////////////////////////
		
		//Contato
		
		
		
		
		///////////////////////////////////////////////////////////////////////////

		//Endereço
		
//		enderecoDao.insert(new Endereco(null, "Perto do bar", 425, "Rosa da Penha", "Porco Ambulante"));
//		enderecoDao.update(new Endereco(null, "Perto do carrossel", 425, "Rosa da Padre", "Porco De Pernas"), enderecoDao.findById(5));
//		enderecoDao.deleteById(enderecoDao.findById(5));
//		System.out.println(enderecoDao.findById(5));
//		
//		for (Endereco e : enderecoDao.findAll()) {
//			System.out.println(e);
//		}
		
		///////////////////////////////////////////////////////////////////////////
		
		//Pessoa
		
//		pessoaDao.insert(new Pessoa(null, "Gugao", null, null, enderecoDao.findById(5)));
//		pessoaDao.update(new Pessoa(null, "Gustavo", "Guga", null, enderecoDao.findById(5)), pessoaDao.findById(5));
//		pessoaDao.deleteById(pessoaDao.findById(6)); VALOR NULL NAO TA ALTERANDO
//		System.out.println(pessoaDao.findById(6));
//		
//		for (Pessoa p : pessoaDao.findAll()) {
//			System.out.println(p);
//		}
		
		///////////////////////////////////////////////////////////////////////////
		
		//Produto
		
//		produtoDao.insert(new Produto(null, "Pizza de calabresa", 15.0, 5, 10.00, categoriaDao.findById(3)));
//		produtoDao.update(new Produto(null, "Pizza de Bolonhesa", 18.00, 7, 9.00, categoriaDao.findById(2)), produtoDao.findById(4));
//		produtoDao.deleteById(produtoDao.findById(1));
//		System.out.println(produtoDao.findById(2));
//		
//		for (Produto p : produtoDao.findAll()) {
//			System.out.println(p);
//		}
		///////////////////////////////////////////////////////////////////////////
		
		//Servico
		
//		servicoDao.insert(new Servico(null, "Confeiteiro"));
//		servicoDao.update(new Servico(null, "Doceiro"), servicoDao.findById(2));
//		servicoDao.deleteById(servicoDao.findById(3));
//		System.out.println(servicoDao.findById(2));
//		
//		for (Servico s : servicoDao.findAll()) {
//			System.out.println(s);
//		}
		
		///////////////////////////////////////////////////////////////////////////
		
		//Tipo
		
//		tipoDao.insert(new Tipo(null, "Instagram"));
//		tipoDao.update(new Tipo(null, "WhatsApp"), tipoDao.findById(34));
//		tipoDao.deleteById(tipoDao.findById(33));
//		System.out.println(tipoDao.findById(35));
//		
//		for (Tipo t : tipoDao.findAll()) {
//			System.out.println(t);
//		}
//		
		
	}

}
