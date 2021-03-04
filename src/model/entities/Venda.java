package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date data;
	private Double total;
	
	private Cliente cliente;
	private Produto produto;
	private Fornecedor fornecedor;
	
	//Constructors
	public Venda() {
		
	}

	public Venda(Integer id, Date data, Double total, Cliente cliente, Produto produto, Fornecedor fornecedor) {
		this.id = id;
		this.data = data;
		this.total = total;
		this.cliente = cliente;
		this.produto = produto;
		this.fornecedor = fornecedor;
	}

	//Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	//Methods
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Venda [id=" + id + ", data=" + data + ", total=" + total + ", cliente=" + cliente + ", produto="
				+ produto + ", fornecedor=" + fornecedor + "]";
	}
	
}
