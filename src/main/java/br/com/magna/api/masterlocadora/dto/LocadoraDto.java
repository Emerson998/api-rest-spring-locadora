package br.com.magna.api.masterlocadora.dto;

import java.util.Objects;

public class LocadoraDto {

	private String endereco;
	private String dvdNome;
	private String cnpj;

	public LocadoraDto() {

	}

	@Override
	public String toString() {
		return "LocadoraDto [endereco=" + endereco + ", dvdNome=" + dvdNome + ", cnpj=" + cnpj + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, dvdNome, endereco);
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
				&& Objects.equals(endereco, other.endereco);
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
