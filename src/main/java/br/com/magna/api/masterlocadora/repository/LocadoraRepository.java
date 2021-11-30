package br.com.magna.api.masterlocadora.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.magna.api.masterlocadora.dto.LocadoraDto;
import br.com.magna.api.masterlocadora.entity.LocadoraEntity;

@Repository
public interface LocadoraRepository extends PagingAndSortingRepository<LocadoraEntity, Long> {
	LocadoraDto save(LocadoraDto locadora);

	Optional<LocadoraEntity> findByCnpj(String cnpj);

	String deleteByCnpj(String cnpj);

}
