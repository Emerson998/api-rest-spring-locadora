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
import br.com.magna.api.masterlocadora.dto.FornecedorDto;
import br.com.magna.api.masterlocadora.entity.FornecedorEntity;
import br.com.magna.api.masterlocadora.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private ModelMapper modelMapper;

	Logger log = LoggerFactory.getLogger(ClienteService.class);

	public Page<FornecedorDto> page(Pageable pageable) {
		try {
			log.info("Busca Todos os Fornecedores");
			Page<FornecedorEntity> fornecedor = fornecedorRepository.findAll(pageable);
			return fornecedor.map(item -> modelMapper.map(item, FornecedorDto.class));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return null;
	}

	public FornecedorDto search(String cnpj) {
		try {
			log.info("Busca individual pelo Cnpj : " + cnpj);
			Optional<FornecedorEntity> fornecedorOptional = fornecedorRepository.findByCnpj(cnpj);
			FornecedorEntity fornecedor = fornecedorOptional.orElseThrow(() -> new NotFoundException());
			FornecedorDto fornecedorDto = converterParaDto(fornecedor);
			return fornecedorDto;
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
			return null;
		} catch (Exception ie) {
			log.error(ie.getMessage());
			return null;
		}
	}

	public FornecedorDto save(FornecedorDto fornecedorDto) {
		FornecedorDto fornecedor = null;
		try {
			log.info("Cadastrando fornecedor do Cnpj: " + fornecedorDto.getCnpj());
			FornecedorEntity cliente = modelMapper.map(fornecedorDto, FornecedorEntity.class);
			FornecedorEntity fornecedorEntity = fornecedorRepository.save(cliente);
			fornecedor = modelMapper.map(fornecedorEntity, FornecedorDto.class);
			log.info("Fornecedor Cadastrado com Sucesso");
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
		} catch (Exception e) {
			throw e;
		}
		return fornecedor;
	}

	public FornecedorDto update(FornecedorDto fornecedorDto) {
		try {
			log.info("Atualizando Fornecedor : " + fornecedorDto.getCnpj());
			FornecedorEntity fornecedor = fornecedorRepository.save(converterParaEntity(fornecedorDto));
			FornecedorDto fornecedorDtoSalvo = converterParaDto(fornecedor);
			log.info("Fornecedor atualizado com sucesso");
			return fornecedorDtoSalvo;
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
		return fornecedorDto;
	}

	public FornecedorDto update(String cnpj, FornecedorDto fornecedorDto) {
		try {
			log.info("Atualizando Fornecedor : " + fornecedorDto.getCnpj());
			FornecedorEntity fornecedor = fornecedorRepository.findByCnpj(cnpj).get();
			FornecedorDto fornecedorDtoAntigo = converterParaDto(fornecedor);
			BeanUtils.copyProperties(fornecedorDto, fornecedorDtoAntigo, " fornecedor");
			FornecedorEntity convertEntity = converterParaEntity(fornecedorDto);
			convertEntity.setId(fornecedor.getId());
			FornecedorEntity fornecedorAtualizado = fornecedorRepository.save(convertEntity);
			log.info("Fornecedor atualizado com sucesso");
			return converterParaDto(fornecedorAtualizado);
		} catch (Exception ie) {
			log.error(ie.getMessage());

		}
		return fornecedorDto;
	}

	public void delete(String cnpj) throws NotFoundException {
		try {
			log.info("Removendo Fornecedor : " + cnpj);
			fornecedorRepository.deleteByCnpj(cnpj);
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
	}

	private FornecedorEntity converterParaEntity(FornecedorDto fornecedorDto) {
		return modelMapper.map(fornecedorDto, FornecedorEntity.class);
	}

	private FornecedorDto converterParaDto(FornecedorEntity fornecedor) {
		return modelMapper.map(fornecedor, FornecedorDto.class);
	}

}
