package br.com.magna.api.masterlocadora.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fornecedor")
public class FornecedorEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String endereco;
	private String lote;
	private String quantidadeDeDvds;
	private String cnpj;;

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

	public LocadoraEntity getLocadora() {
		return locadora;
	}

	public void setLocadora(LocadoraEntity locadora) {
		this.locadora = locadora;
	}

	@Override
	public String toString() {
		return "FornecedorEntity [id=" + id + ", endereco=" + endereco + ", lote=" + lote + ", quantidadeDeDvds="
				+ quantidadeDeDvds + ", cnpj=" + cnpj + ", locadora=" + locadora + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, endereco, id, locadora, lote, quantidadeDeDvds);
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
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(id, other.id) && Objects.equals(locadora, other.locadora)
				&& Objects.equals(lote, other.lote) && Objects.equals(quantidadeDeDvds, other.quantidadeDeDvds);
	}

}
