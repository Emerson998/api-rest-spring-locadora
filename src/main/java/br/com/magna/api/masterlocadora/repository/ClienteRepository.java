package br.com.magna.api.masterlocadora.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.magna.api.masterlocadora.dto.ClienteDto;
import br.com.magna.api.masterlocadora.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<ClienteEntity, Long> {
	ClienteDto save(ClienteDto cliente);

	Optional<ClienteEntity> findByCpf(String cpf);

	ClienteEntity save(String locadora);

	String deleteByCpf(String cpf);

}
