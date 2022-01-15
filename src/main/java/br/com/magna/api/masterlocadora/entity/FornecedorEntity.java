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
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "fornecedor")
public class FornecedorEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String endereco;
	@NotBlank
	private String lote;
	@NotBlank
	private Long quantidadeDeDvds;
	@NotBlank
	private String cnpj;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data = LocalDate.now();

	@OneToOne
	private LocadoraEntity locadora;

	public FornecedorEntity() {

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

	public LocadoraEntity getLocadora() {
		return locadora;
	}

	public void setLocadora(LocadoraEntity locadora) {
		this.locadora = locadora;
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
		return "FornecedorEntity [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", lote=" + lote
				+ ", quantidadeDeDvds=" + quantidadeDeDvds + ", cnpj=" + cnpj + ", data=" + data + ", locadora="
				+ locadora + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, data, endereco, id, locadora, lote, nome, quantidadeDeDvds);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FornecedorEntity other = (FornecedorEntity) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(data, other.data)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(id, other.id)
				&& Objects.equals(locadora, other.locadora) && Objects.equals(lote, other.lote)
				&& Objects.equals(nome, other.nome) && Objects.equals(quantidadeDeDvds, other.quantidadeDeDvds);
	}

}
