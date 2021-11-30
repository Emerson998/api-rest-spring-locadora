package br.com.magna.api.masterlocadora.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magna.api.masterlocadora.dto.FornecedorDto;
import br.com.magna.api.masterlocadora.entity.FornecedorEntity;
import br.com.magna.api.masterlocadora.repository.FornecedorRepository;

@Service
public class FornecedorService {
	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Paginacao
	public Page<FornecedorDto> buscaEspecifica(Pageable pageable) {
		Page<FornecedorEntity> fornecedor = fornecedorRepository.findAll(pageable);
		return fornecedor.map(item -> modelMapper.map(item, FornecedorDto.class));
	}

	// Buscando Fonecedor
	public FornecedorDto getCnpj(String cnpj) throws NotFoundException {
		FornecedorEntity fornecedor = fornecedorRepository.findByCnpj(cnpj);
		FornecedorDto fornecedorDto = converterParaDto(fornecedor);

		if (fornecedorDto == null) {
			throw new NotFoundException();
		}
		return fornecedorDto;
	}

	// Salvando Fonecedor
	public FornecedorDto salvarFornecedorDto(FornecedorDto fornecedorDto) {
		try {
			FornecedorEntity fornecedor = fornecedorRepository.save(converParaEntity(fornecedorDto));
			FornecedorDto fornecedorDtoSalvo = converterParaDto(fornecedor);
			return fornecedorDtoSalvo;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fornecedorDto;
	}

	// Atualizando Fornecedor
	public FornecedorDto alterarFornecedorDto(FornecedorDto fornecedorDto) {
		try {
			FornecedorEntity fornecedor = fornecedorRepository.save(converParaEntity(fornecedorDto));
			FornecedorDto fornecedorDtoSalvo = converterParaDto(fornecedor);
			return fornecedorDtoSalvo;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fornecedorDto;
	}

	// Atualizando Fornecedor
	public FornecedorDto update(String cnpj, FornecedorDto fornecedorDto) throws NotFoundException {
		FornecedorEntity fornecedor = fornecedorRepository.findByCnpj(cnpj);
		FornecedorDto fornecedorDtoAntigo = converterParaDto(fornecedor);
		BeanUtils.copyProperties(fornecedorDto, fornecedorDtoAntigo, "fornecedor");
		FornecedorEntity convertEntity = converParaEntity(fornecedorDto);
		convertEntity.setId(fornecedor.getId());
		FornecedorEntity fornecedorAtualizado = fornecedorRepository.save(convertEntity);
		return converterParaDto(fornecedorAtualizado);
	}

	// Deletando um usuario
	public void delete(String cnpj) throws NotFoundException {
		fornecedorRepository.deleteByCnpj(cnpj);
	}

	// Conversores
	private FornecedorDto converterParaDto(FornecedorEntity fornecedor) {
		return modelMapper.map(fornecedor, FornecedorDto.class);
	}

	private FornecedorEntity converParaEntity(FornecedorDto fornecedorDto) {
		return modelMapper.map(fornecedorDto, FornecedorEntity.class);
	}

}
