package br.com.magna.api.masterlocadora.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magna.api.masterlocadora.dto.LocadoraDto;
import br.com.magna.api.masterlocadora.entity.LocadoraEntity;
import br.com.magna.api.masterlocadora.repository.LocadoraRepository;

@Service
public class LocadoraService {
	@Autowired
	private LocadoraRepository locadoraRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Paginacao
	public Page<LocadoraDto> buscaEspecifica(Pageable pageable) {
		Page<LocadoraEntity> locadora = locadoraRepository.findAll(pageable);
		return locadora.map(item -> modelMapper.map(item, LocadoraDto.class));
	}

	// Buscando Locadora
	public LocadoraDto getCnpj(String cnpj) throws NotFoundException {
		LocadoraEntity locadora = locadoraRepository.findByCnpj(cnpj);
		LocadoraDto locadoraDto = converterParaDto(locadora);

		if (locadoraDto == null) {
			throw new NotFoundException();
		}
		return locadoraDto;
	}

	// Salvando a Locadora
	public LocadoraDto salvarLocadoraDto(LocadoraDto locadoraDto) {
		try {
			LocadoraEntity locadora = locadoraRepository.save(converParaEntity(locadoraDto));
			LocadoraDto clienteDtoSalvo = converterParaDto(locadora);
			return clienteDtoSalvo;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return locadoraDto;
	}

	// Atualizando Locadora
	public LocadoraDto alterarClienteDto(LocadoraDto locadoraDto) {
		try {
			LocadoraEntity locadora = locadoraRepository.save(converParaEntity(locadoraDto));
			LocadoraDto locadoraDtoSalvo = converterParaDto(locadora);
			return locadoraDtoSalvo;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return locadoraDto;
	}

	// Atualizando Locadora
	public LocadoraDto update(String cnpj, LocadoraDto locadoraDto) throws NotFoundException {
		LocadoraEntity locadora = locadoraRepository.findByCnpj(cnpj);
		LocadoraDto locadoraDtoAntigo = converterParaDto(locadora);
		BeanUtils.copyProperties(locadoraDto, locadoraDtoAntigo, "locadora");
		LocadoraEntity convertEntity = converParaEntity(locadoraDto);
		convertEntity.setId(locadora.getId());
		LocadoraEntity locadoraAtualizado = locadoraRepository.save(convertEntity);
		return converterParaDto(locadoraAtualizado);
	}

	// Conversores
	private LocadoraDto converterParaDto(LocadoraEntity locadora) {
		return modelMapper.map(locadora, LocadoraDto.class);
	}

	private LocadoraEntity converParaEntity(LocadoraDto locadoraDto) {
		return modelMapper.map(locadoraDto, LocadoraEntity.class);
	}

}
