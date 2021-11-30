package br.com.magna.api.masterlocadora.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.validation.InvalidStateException;
import br.com.magna.api.masterlocadora.dto.LocadoraDto;
import br.com.magna.api.masterlocadora.entity.LocadoraEntity;
import br.com.magna.api.masterlocadora.repository.LocadoraRepository;

@Service
public class LocadoraService {

	@Autowired
	private LocadoraRepository locadoraRepository;

	@Autowired
	private ModelMapper modelMapper;

	Logger log = LoggerFactory.getLogger(LocadoraService.class);

	// Paginacao
	public Page<LocadoraDto> buscarTodos(Pageable pageable) {
		try {
			Page<LocadoraEntity> locadora = locadoraRepository.findAll(pageable);
			return locadora.map(item -> modelMapper.map(item, LocadoraDto.class));
		} catch (Exception ex) {
			throw ex;
		}
	}

	// Buscando Locadoras
	public LocadoraDto getLogin(String cnpj) throws NotFoundException {
		try {
			log.info("Validando CPF : ");
			Optional<LocadoraEntity> locadoraOptional = locadoraRepository.findByCnpj(cnpj);
			LocadoraEntity locadora = locadoraOptional.orElseThrow(() -> new NotFoundException());
			LocadoraDto locadoraDto = converterParaDto(locadora);
			return locadoraDto;
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
			return null;
		} catch (Exception ie) {
			log.error(ie.getMessage());
			return null;
		}
	}

	// Salvando Locadora
	public LocadoraDto salvandoLocadoraDto(LocadoraDto locadoraDtoSave) throws Exception {
		LocadoraDto locadoraRetorno = null;
		try {
			log.info("Validando Cnpj: " + locadoraDtoSave.getCnpj());
			LocadoraEntity locadora = modelMapper.map(locadoraDtoSave, LocadoraEntity.class);
			LocadoraEntity locadoraEntity = locadoraRepository.save(locadora);
			locadoraRetorno = modelMapper.map(locadoraEntity, LocadoraDto.class);
			log.info("Fornecedor Criado com Sucesso");
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
		} catch (Exception e) {
			throw e;
		}
		return locadoraRetorno;
	}

	// Atualizando Locadora
	public LocadoraDto alterarLocadoraDto(LocadoraDto locadoraDto) {
		try {
			log.info("Atualizando Cliente : ");
			LocadoraEntity locadora = locadoraRepository.save(converterParaEntity(locadoraDto));
			LocadoraDto locadoraDtoSalvo = converterParaDto(locadora);
			return locadoraDtoSalvo;
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
		return locadoraDto;
	}

	// Atualizando Locadora
	public LocadoraDto update(String cnpj, LocadoraDto locadoraDto) throws NotFoundException {
		try {
			LocadoraEntity locadora = locadoraRepository.findByCnpj(cnpj).get();
			LocadoraDto locadoraDtoAntigo = converterParaDto(locadora);
			BeanUtils.copyProperties(locadoraDto, locadoraDtoAntigo, "locadora");
			LocadoraEntity convertEntity = converterParaEntity(locadoraDto);
			convertEntity.setId(locadora.getId());
			LocadoraEntity locadoraAtualizado = locadoraRepository.save(convertEntity);
			return converterParaDto(locadoraAtualizado);
		} catch (Exception ie) {
			log.error(ie.getMessage());

		}
		return locadoraDto;
	}

	// Delete Locadora
	public void delete(String cnpj) throws NotFoundException {
		try {
			log.info("Removendo Locadora : ");
			locadoraRepository.deleteByCnpj(cnpj);
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
	}

	// Conversores
	private LocadoraEntity converterParaEntity(LocadoraDto locadoraDto) {
		return modelMapper.map(locadoraDto, LocadoraEntity.class);
	}

	private LocadoraDto converterParaDto(LocadoraEntity locadora) {
		return modelMapper.map(locadora, LocadoraDto.class);
	}

}
