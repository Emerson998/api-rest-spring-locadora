package br.com.magna.api.masterlocadora.dto;

import java.util.Objects;

import br.com.magna.api.masterlocadora.entity.ClienteEntity;
import br.com.magna.api.masterlocadora.entity.LocadoraEntity;

public class ClienteDto {

	private String nome;
	private String cpf;
	private LocadoraEntity locadora;

	public ClienteDto() {

	}

	public LocadoraEntity getLocadora() {
		return locadora;
	}

	public void setLocadora(LocadoraEntity locadora) {
		this.locadora = locadora;
	}

	public ClienteDto(ClienteEntity cliente) {
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "ClienteDto [nome=" + nome + ", cpf=" + cpf + ", locadora=" + locadora + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, locadora, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteDto other = (ClienteDto) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(locadora, other.locadora)
				&& Objects.equals(nome, other.nome);
	}

}
