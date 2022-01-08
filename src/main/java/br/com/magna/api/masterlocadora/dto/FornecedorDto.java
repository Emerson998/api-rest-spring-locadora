package br.com.magna.api.masterlocadora.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FornecedorDto {

	private String nome;
	private String endereco;
	private String lote;
	private Long quantidadeDeDvds;
	private String cnpj;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data = LocalDate.now();

	public FornecedorDto() {

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "FornecedorDto [nome=" + nome + ", endereco=" + endereco + ", lote=" + lote + ", quantidadeDeDvds="
				+ quantidadeDeDvds + ", cnpj=" + cnpj + ", data=" + data + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, data, endereco, lote, nome, quantidadeDeDvds);
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
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(data, other.data)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(lote, other.lote)
				&& Objects.equals(nome, other.nome) && Objects.equals(quantidadeDeDvds, other.quantidadeDeDvds);
	}

}