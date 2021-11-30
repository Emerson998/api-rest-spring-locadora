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

	// Paginacao
	public Page<FornecedorDto> paginacaoDaApi(Pageable pageable) {
		try {
			Page<FornecedorEntity> fornecedor = fornecedorRepository.findAll(pageable);
			return fornecedor.map(item -> modelMapper.map(item, FornecedorDto.class));
		} catch (Exception ex) {
			throw ex;
		}
	}

	// Buscando Fornecedores
	public FornecedorDto buscaFornecedor(String cnpj) throws NotFoundException {
		try {
			log.info("Validando CPF : ");
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

	// Salvando Fornecedores
	public FornecedorDto salvandoFornecedorDto(FornecedorDto fornecedorDtoSave) throws Exception {
		FornecedorDto fornecedorRetorno = null;
		try {
			log.info("Validando Cnpj: " + fornecedorDtoSave.getCnpj());
			FornecedorEntity cliente = modelMapper.map(fornecedorDtoSave, FornecedorEntity.class);
			FornecedorEntity fornecedorEntity = fornecedorRepository.save(cliente);
			fornecedorRetorno = modelMapper.map(fornecedorEntity, FornecedorDto.class);
			log.info("Fornecedor Criado com Sucesso");
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
		} catch (Exception e) {
			throw e;
		}
		return fornecedorRetorno;
	}

	// Atualizando Fornecedores
	public FornecedorDto alterarFornecedorDto(FornecedorDto fornecedorDto) {
		try {
			log.info("Atualizando Cliente : ");
			FornecedorEntity fornecedor = fornecedorRepository.save(converterParaEntity(fornecedorDto));
			FornecedorDto fornecedorDtoSalvo = converterParaDto(fornecedor);
			return fornecedorDtoSalvo;
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
		return fornecedorDto;
	}

	// Atualizando Fornecedores
	public FornecedorDto atualiza(String cnpj, FornecedorDto fornecedorDto) throws NotFoundException {
		try {
			FornecedorEntity fornecedor = fornecedorRepository.findByCnpj(cnpj).get();
			FornecedorDto fornecedorDtoAntigo = converterParaDto(fornecedor);
			BeanUtils.copyProperties(fornecedorDto, fornecedorDtoAntigo, " fornecedor");
			FornecedorEntity convertEntity = converterParaEntity(fornecedorDto);
			convertEntity.setId(fornecedor.getId());
			FornecedorEntity fornecedorAtualizado = fornecedorRepository.save(convertEntity);
			return converterParaDto(fornecedorAtualizado);
		} catch (Exception ie) {
			log.error(ie.getMessage());

		}
		return fornecedorDto;
	}

	// Delete o Fornecedor
	public void delete(String cnpj) throws NotFoundException {
		try {
			log.info("Removendo Cliente : ");
			fornecedorRepository.deleteByCnpj(cnpj);
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
	}

	// Conversores
	private FornecedorEntity converterParaEntity(FornecedorDto fornecedorDto) {
		return modelMapper.map(fornecedorDto, FornecedorEntity.class);
	}

	private FornecedorDto converterParaDto(FornecedorEntity fornecedor) {
		return modelMapper.map(fornecedor, FornecedorDto.class);
	}

}
