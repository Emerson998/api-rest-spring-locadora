package br.com.magna.api.masterlocadora.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "locadora")
public class LocadoraEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String endereco;
	private String lote;
	private Long quantidadeDeDvds;
	private String cnpj;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data = LocalDate.now();;

	@OneToOne
	private FornecedorEntity fornecedor;

	public LocadoraEntity() {

	}

	public LocadoraEntity(Long id, String nome, String endereco, String lote, Long quantidadeDeDvds, String cnpj,
			LocalDate data, FornecedorEntity fornecedor) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.lote = lote;
		this.quantidadeDeDvds = quantidadeDeDvds;
		this.cnpj = cnpj;
		this.data = data;
		this.fornecedor = fornecedor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Long getQuantidadeDeDvds() {
		return quantidadeDeDvds;
	}

	public void setQuantidadeDeDvds(Long quantidadeDeDvds) {
		this.quantidadeDeDvds = quantidadeDeDvds;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public FornecedorEntity getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorEntity fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public String toString() {
		return "LocadoraEntity [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", lote=" + lote
				+ ", quantidadeDeDvds=" + quantidadeDeDvds + ", cnpj=" + cnpj + ", data=" + data + ", fornecedor="
				+ fornecedor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, data, endereco, fornecedor, id, lote, nome, quantidadeDeDvds);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocadoraEntity other = (LocadoraEntity) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(data, other.data)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(fornecedor, other.fornecedor)
				&& Objects.equals(id, other.id) && Objects.equals(lote, other.lote) && Objects.equals(nome, other.nome)
				&& Objects.equals(quantidadeDeDvds, other.quantidadeDeDvds);
	}

}
