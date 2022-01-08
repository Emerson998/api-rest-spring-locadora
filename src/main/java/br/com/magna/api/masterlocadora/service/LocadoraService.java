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

	Logger log = LoggerFactory.getLogger(ClienteService.class);

	public Page<LocadoraDto> page(Pageable pageable) {
		try {
			log.info("Busca Todos os Fornecedores");
			Page<LocadoraEntity> locadora = locadoraRepository.findAll(pageable);
			return locadora.map(item -> modelMapper.map(item, LocadoraDto.class));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return null;
	}

	public LocadoraDto search(String cnpj) {
		try {
			log.info("Busca individual pelo Cnpj : " + cnpj);
			Optional<LocadoraEntity> locadoraOptional = locadoraRepository.findByCnpj(cnpj);
			LocadoraEntity locadora = locadoraOptional.orElseThrow(() -> new NotFoundException());
			LocadoraDto locadoraDto = convertDto(locadora);
			return locadoraDto;
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
			return null;
		} catch (Exception ie) {
			log.error(ie.getMessage());
			return null;
		}
	}

	public LocadoraDto save(LocadoraDto locadoraDto) {
		LocadoraDto locadoraRetorno = null;
		try {
			log.info("Cadastrando locadora do Cnpj: " + locadoraDto.getCnpj());
			LocadoraEntity locadora = modelMapper.map(locadoraDto, LocadoraEntity.class);
			LocadoraEntity locadoraEntity = locadoraRepository.save(locadora);
			locadoraRetorno = modelMapper.map(locadoraEntity, LocadoraDto.class);
			log.info("Locadora Cadastrada com Sucesso");
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
		} catch (Exception e) {
			throw e;
		}
		return locadoraRetorno;
	}

	public LocadoraDto update(LocadoraDto locadoraDto) {
		try {
			log.info("Atualizando Locadora : " + locadoraDto.getCnpj());
			LocadoraEntity locadora = locadoraRepository.save(convertEntity(locadoraDto));
			LocadoraDto locadoraDtoSave = convertDto(locadora);
			log.info("Fornecedor atualizado com sucesso");
			return locadoraDtoSave;
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
		return locadoraDto;
	}

	public LocadoraDto update(String cnpj, LocadoraDto locadoraDto) {
		try {
			log.info("Atualizando Locadora : " + locadoraDto.getCnpj());
			LocadoraEntity locadora = locadoraRepository.findByCnpj(cnpj).get();
			LocadoraDto locadoraDtoOld = convertDto(locadora);
			BeanUtils.copyProperties(locadoraDto, locadoraDtoOld, " locadora");
			LocadoraEntity convertEntity = convertEntity(locadoraDto);
			convertEntity.setId(locadora.getId());
			LocadoraEntity locadoraUpdate = locadoraRepository.save(convertEntity);
			log.info("Locadora atualizada com sucesso");
			return convertDto(locadoraUpdate);
		} catch (Exception ie) {
			log.error(ie.getMessage());

		}
		return locadoraDto;
	}

	public void delete(String cnpj) throws NotFoundException {
		try {
			log.info("Removendo Locadora : " + cnpj);
			locadoraRepository.deleteByCnpj(cnpj);
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
	}

	private LocadoraEntity convertEntity(LocadoraDto locadoraDto) {
		return modelMapper.map(locadoraDto, LocadoraEntity.class);
	}

	private LocadoraDto convertDto(LocadoraEntity locadora) {
		return modelMapper.map(locadora, LocadoraDto.class);
	}

}
