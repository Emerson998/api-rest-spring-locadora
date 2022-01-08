package br.com.magna.api.masterlocadora.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.magna.api.masterlocadora.entity.LocadoraEntity;

public class ClienteDto {

	private String nome;
	private String cpf;
	private Long senha;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data = LocalDate.now();

	private LocadoraEntity locadora;

	public ClienteDto() {

	}

	public ClienteDto(String nome, String cpf, Long senha, LocalDate data, LocadoraEntity locadora) {
		this.nome = nome;
		this.cpf = cpf;
		this.senha = senha;
		this.data = data;
		this.locadora = locadora;
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

	public Long getSenha() {
		return senha;
	}

	public void setSenha(Long senha) {
		this.senha = senha;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ClienteDto [nome=" + nome + ", cpf=" + cpf + ", senha=" + senha + ", data=" + data + ", locadora="
				+ locadora + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, data, locadora, nome, senha);
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
		return Objects.equals(cpf, other.cpf) && Objects.equals(data, other.data)
				&& Objects.equals(locadora, other.locadora) && Objects.equals(nome, other.nome)
				&& Objects.equals(senha, other.senha);
	}

}
