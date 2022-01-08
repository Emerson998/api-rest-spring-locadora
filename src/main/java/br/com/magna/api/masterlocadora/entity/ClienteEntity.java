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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cliente")
public class ClienteEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	private Long senha;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data = LocalDate.now();

	@OneToOne
	private LocadoraEntity locadora;

	public ClienteEntity() {

	}

	public ClienteEntity(Long id, String nome, String cpf, Long senha, LocalDate data, LocadoraEntity locadora) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.senha = senha;
		this.data = data;
		this.locadora = locadora;
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
		return "ClienteEntity [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", senha=" + senha + ", data=" + data
				+ ", locadora=" + locadora + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, data, id, locadora, nome, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteEntity other = (ClienteEntity) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(data, other.data) && Objects.equals(id, other.id)
				&& Objects.equals(locadora, other.locadora) && Objects.equals(nome, other.nome)
				&& Objects.equals(senha, other.senha);
	}

}
