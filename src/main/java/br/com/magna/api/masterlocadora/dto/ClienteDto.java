package br.com.magna.api.masterlocadora.dto;

import java.util.Objects;

import br.com.magna.api.masterlocadora.entity.ClienteEntity;
import br.com.magna.api.masterlocadora.entity.LocadoraEntity;

public class ClienteDto {

	private Long id;
	private String nome;
	private String cpf;
	private LocadoraEntity locadora;

	public ClienteDto() {

	}

	public ClienteDto(Long id, String nome, String cpf, LocadoraEntity locadora) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.locadora = locadora;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, cpf, nome);
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
		return Objects.equals(id, other.id) && Objects.equals(cpf, other.cpf) && Objects.equals(nome, other.nome);
	}

}
