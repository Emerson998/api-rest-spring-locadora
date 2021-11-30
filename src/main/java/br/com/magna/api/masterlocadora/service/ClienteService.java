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

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.magna.api.masterlocadora.dto.ClienteDto;
import br.com.magna.api.masterlocadora.entity.ClienteEntity;
import br.com.magna.api.masterlocadora.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ModelMapper modelMapper;

	Logger log = LoggerFactory.getLogger(ClienteService.class);

	// Paginacao
	public Page<ClienteDto> paginacaoDaApi(Pageable pageable) {
		try {
			Page<ClienteEntity> cliente = clienteRepository.findAll(pageable);
			return cliente.map(item -> modelMapper.map(item, ClienteDto.class));
		} catch (Exception ex) {
			throw ex;
		}
	}

	// Buscando Clientes
	public ClienteDto buscaCliente(String cpf) throws NotFoundException {
		try {
			log.info("Validando CPF : ");
			Optional<ClienteEntity> clienteOptional = clienteRepository.findByCpf(cpf);
			ClienteEntity cliente = clienteOptional.orElseThrow(() -> new NotFoundException());
			ClienteDto usuarioDto = converterParaDto(cliente);
			return usuarioDto;
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
			return null;
		} catch (Exception ie) {
			log.error(ie.getMessage());
			return null;
		}
	}

	// Salvando Clientes
	public ClienteDto salvandoClienteDto(ClienteDto clienteDtoSave) throws Exception {
		ClienteDto clienteRetorno = null;
		try {
			log.info("Validando CPF: " + clienteDtoSave.getCpf());
			validandoCpf(clienteDtoSave.getCpf());
			ClienteEntity cliente = modelMapper.map(clienteDtoSave, ClienteEntity.class);
			ClienteEntity clienteEntity = clienteRepository.save(cliente);
			clienteRetorno = modelMapper.map(clienteEntity, ClienteDto.class);
			log.info("Cliente Criado com Sucesso");
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
		} catch (Exception e) {
			throw e;
		}
		return clienteRetorno;
	}

	// Atualizando Clientes
	public ClienteDto alterarClienteDto(ClienteDto clienteDto) {
		try {
			log.info("Atualizando Cliente : ");
			ClienteEntity cliente = clienteRepository.save(converterParaEntity(clienteDto));
			ClienteDto clienteDtoSalvo = converterParaDto(cliente);
			return clienteDtoSalvo;
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
		return clienteDto;
	}

	// Atualizando Clientes
	public ClienteDto atualiza(String cpf, ClienteDto clienteDto) throws NotFoundException {
		try {
			ClienteEntity cliente = clienteRepository.findByCpf(cpf).get();
			ClienteDto clienteDtoAntigo = converterParaDto(cliente);
			BeanUtils.copyProperties(clienteDto, clienteDtoAntigo, "cliente");
			ClienteEntity convertEntity = converterParaEntity(clienteDto);
			convertEntity.setId(cliente.getId());
			ClienteEntity clienteAtualizado = clienteRepository.save(convertEntity);
			return converterParaDto(clienteAtualizado);
		} catch (Exception ie) {
			log.error(ie.getMessage());

		}
		return clienteDto;
	}

	// Apenas Cpfs Validos
	public void validandoCpf(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		cpfValidator.assertValid(cpf);
	}

	// Delete Um Cliente
	public void delete(String cpf) throws NotFoundException {
		try {
			log.info("Removendo Cliente : ");
			clienteRepository.deleteByCpf(cpf);
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
	}

	// Conversores
	private ClienteEntity converterParaEntity(ClienteDto clienteDto) {
		return modelMapper.map(clienteDto, ClienteEntity.class);
	}

	private ClienteDto converterParaDto(ClienteEntity cliente) {
		return modelMapper.map(cliente, ClienteDto.class);
	}

}
