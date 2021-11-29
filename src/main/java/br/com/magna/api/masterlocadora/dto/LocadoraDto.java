package br.com.magna.api.masterlocadora.dto;

import java.util.Objects;

public class LocadoraDto {
	private Long id;
	private String endereco;
	private String dvdNome;
	private String cnpj;

	public LocadoraDto() {

	}

	public LocadoraDto(Long id, String endereco, String dvdNome, String cnpj) {
		this.id = id;
		this.endereco = endereco;
		this.dvdNome = dvdNome;
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "LocadoraDto [id=" + id + ", endereco=" + endereco + ", dvdNome=" + dvdNome + ", cnpj=" + cnpj + "]";
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
		LocadoraDto other = (LocadoraDto) obj;
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

}
