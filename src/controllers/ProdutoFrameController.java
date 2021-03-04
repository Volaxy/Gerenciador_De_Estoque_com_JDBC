package controllers;

import java.util.List;

import dao.ProdutoDao;
import model.entities.Categoria;
import model.entities.Produto;
import model.factories.DaoFactory;
import services.InputsService;

public class ProdutoFrameController {

	public static void insertProduto(String nomeText, String quantidadeText, String custoText, String precoText) {
		if(custoText.indexOf(",") > 0) {
			custoText = custoText.replace(",", ".");
		}
		
		if(precoText.indexOf(",") > 0) {
			precoText = precoText.replace(",", ".");
		}
		
		InputsService.validateNumbers(quantidadeText, custoText, precoText);
		
		ProdutoDao produtoDao = DaoFactory.createProdutoDao();
		
		produtoDao.insert(instantiateProduto(null, nomeText, precoText, quantidadeText, custoText));
	}

	public static List<Produto> updateTable() {
		ProdutoDao produtoDao = DaoFactory.createProdutoDao();
		
		return produtoDao.findAll();
	}

	public static void deleteProduto(Object[] produto) {
		ProdutoDao produtoDao = DaoFactory.createProdutoDao();	
		
		produtoDao.deleteById(instantiateProduto(produto[0], produto[1], produto[2], produto[3], produto[4]));
	}
	
	public static void updateProduto(String id, String nome, String preco, String quantidade, String custo) {
			ProdutoDao produtoDao = DaoFactory.createProdutoDao();
			
			produtoDao.update(instantiateProduto(id, nome, preco, quantidade, custo), produtoDao.findById(Integer.parseInt(id)));
	}
	
	private static Produto instantiateProduto(Object id, Object nome, Object preco, Object quantidade, Object custo) {
		Integer idProduto = Integer.parseInt(id.toString());
		String nomeProduto = nome.toString();
		Double precoProduto = Double.parseDouble(preco.toString());
		Integer quantidadeProduto = Integer.parseInt(quantidade.toString());
		Double custoProduto = Double.parseDouble(custo.toString());
		
		/*
		 * TODO Fazer uma consulta pelo JScrollPane para consultar a escolha da categoria
		 * Por hora, simplesmente é passado uma categoria (que contém um id existente
		 * no banco), para inserir o produto no banco de dados
		 */
		Categoria categoria = new Categoria(3, "Doces");
		
		return new Produto(idProduto, nomeProduto, precoProduto, quantidadeProduto, custoProduto, categoria);
	}
	
}
