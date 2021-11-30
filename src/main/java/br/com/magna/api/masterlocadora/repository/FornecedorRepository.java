package br.com.magna.api.masterlocadora.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.magna.api.masterlocadora.dto.FornecedorDto;
import br.com.magna.api.masterlocadora.entity.FornecedorEntity;

@Repository
public interface FornecedorRepository extends PagingAndSortingRepository<FornecedorEntity, Long> {
	FornecedorDto save(FornecedorDto fornecedor);

	Optional<FornecedorEntity> findByCnpj(String cnpj);

	String deleteByCnpj(String cnpj);
}