package br.com.magna.api.masterlocadora.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.magna.api.masterlocadora.dto.ClienteDto;

@Entity
@Table(name = "locadora")
public class LocadoraEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String endereco;

	private String dvdNome;

	private String cnpj;

	@OneToOne
	private FornecedorEntity fornecedor;

	public LocadoraEntity() {

	}

	public LocadoraEntity(String titulo, String sinopse, String nomeDvd, String cnpj) {

	}

	public LocadoraEntity(Long id, String endereco, String dvdNome, String cnpj) {
		
		this.id = id;
		this.endereco = endereco;
		this.dvdNome = dvdNome;
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "LocadoraEntity [id=" + id + ", endereco=" + endereco + ", dvdNome=" + dvdNome + ", cnpj=" + cnpj + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, dvdNome, endereco, id);
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
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(dvdNome, other.dvdNome)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(id, other.id);
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

	public String getDvdNome() {
		return dvdNome;
	}

	public void setDvdNome(String dvdNome) {
		this.dvdNome = dvdNome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public static String setLocadora(String locadora) {
		return locadora;

	}

	public static ClienteDto setLocadora(ClienteDto locadora) {
		return locadora;

	}

}
