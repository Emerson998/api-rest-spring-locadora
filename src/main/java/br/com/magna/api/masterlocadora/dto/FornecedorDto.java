package br.com.magna.api.masterlocadora.dto;

import java.util.Objects;

public class FornecedorDto {
	private Long id;
	private String endereco;
	private String lote;
	private String quantidadeDeDvds;
	private String cnpj;

	public FornecedorDto() {

	}

	public FornecedorDto(Long id, String endereco, String lote, String quantidadeDeDvds, String cnpj) {
		super();
		this.id = id;
		this.endereco = endereco;
		this.lote = lote;
		this.quantidadeDeDvds = quantidadeDeDvds;
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "FornecedorDto [id=" + id + ", endereco=" + endereco + ", lote=" + lote + ", quantidadeDeDvds="
				+ quantidadeDeDvds + ", cnpj=" + cnpj + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, endereco, id, lote, quantidadeDeDvds);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FornecedorDto other = (FornecedorDto) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(id, other.id) && Objects.equals(lote, other.lote)
				&& Objects.equals(quantidadeDeDvds, other.quantidadeDeDvds);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getQuantidadeDeDvds() {
		return quantidadeDeDvds;
	}

	public void setQuantidadeDeDvds(String quantidadeDeDvds) {
		this.quantidadeDeDvds = quantidadeDeDvds;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}