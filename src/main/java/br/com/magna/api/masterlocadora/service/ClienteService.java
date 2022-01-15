package br.com.magna.api.masterlocadora.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.magna.api.masterlocadora.dto.ClienteDto;
import br.com.magna.api.masterlocadora.entity.ClienteEntity;
import br.com.magna.api.masterlocadora.exception.ServiceExceptionLocadora;
import br.com.magna.api.masterlocadora.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ModelMapper modelMapper;

	Logger log = LoggerFactory.getLogger(ClienteService.class);

	public Page<ClienteDto> page(Pageable pageable) {
		log.info("Busca Todos os Clientes");
		Page<ClienteEntity> cliente = clienteRepository.findAll(pageable);
		return cliente.map(item -> modelMapper.map(item, ClienteDto.class));
	}

	public ClienteDto search(String cpf) {
		try {
			log.info("Busca individual pelo Cpf : " + cpf);
			Optional<ClienteEntity> clienteOptional = clienteRepository.findByCpf(cpf);
			ClienteEntity cliente = clienteOptional.orElseThrow(() -> new ServiceExceptionLocadora("Erro na busca por este cliente, verifique o cpf e tente novamente"));
			ClienteDto clienteDto = converter(cliente);
			return clienteDto;
		} catch (InvalidStateException ie) {
			log.error(ie.getMessage());
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
		return null;
	}

	public ClienteDto save(ClienteDto clienteDto) {
		ClienteDto clienteRetorno = null;
		try {
			log.info("Cadastrando e validando CPF: " + clienteDto.getCpf());
			validatingCpf(clienteDto.getCpf());
			ClienteEntity cliente = modelMapper.map(clienteDto, ClienteEntity.class);
			ClienteEntity clienteEntity = clienteRepository.save(cliente);
			clienteRetorno = modelMapper.map(clienteEntity, ClienteDto.class);
			log.info("Cliente cadastrado com Sucesso");
		} catch (InvalidStateException ie) {
			log.error("Erro" + ie);
		}
		return clienteRetorno;
	}

	public ClienteDto update(ClienteDto clienteDto) {
		try {
			log.info("Atualizando Cliente : " + clienteDto.getCpf());
			ClienteEntity cliente = clienteRepository.save(convertEntity(clienteDto));
			ClienteDto clienteDtoSave = converter(cliente);
			log.info("Cliente atualizado com Sucesso" + clienteDto.getCpf());
			return clienteDtoSave;
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
		return clienteDto;
	}

	public ClienteDto update(String cpf, ClienteDto clienteDto) {
		try {
			log.info("Atualizando Cliente : " + clienteDto.getCpf());
			ClienteEntity cliente = clienteRepository.findByCpf(cpf).get();
			ClienteDto customerDtoOld = converter(cliente);
			BeanUtils.copyProperties(clienteDto, customerDtoOld, "cliente");
			ClienteEntity convertEntity = convertEntity(clienteDto);
			convertEntity.setId(cliente.getId());
			ClienteEntity clienteUpdate = clienteRepository.save(convertEntity);
			log.info("Cliente atualizado com Sucesso");
			return converter(clienteUpdate);
		} catch (Exception ie) {
			log.error(ie.getMessage());

		}
		return clienteDto;
	}

	public boolean validatingCpf(String cpf) {
		try {
			CPFValidator cpfValidator = new CPFValidator();
			cpfValidator.assertValid(cpf);
		} catch (Exception e) {
			log.error(e.getMessage());
			return true;
		}
		return false;

	}

	public void delete(String cpf){
		try {
			log.info("Removendo Cliente : " + cpf);
			clienteRepository.deleteByCpf(cpf);
			log.info("Remocao concluida com sucesso ");
		} catch (Exception ie) {
			log.error(ie.getMessage());
		}
	}

	private ClienteEntity convertEntity(ClienteDto clienteDto) {
		return modelMapper.map(clienteDto, ClienteEntity.class);
	}

	private ClienteDto converter(ClienteEntity cliente) {
		return modelMapper.map(cliente, ClienteDto.class);
	}

}
