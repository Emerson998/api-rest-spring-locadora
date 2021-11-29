package br.com.magna.api.masterlocadora.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.magna.api.masterlocadora.dto.ClienteDto;
import br.com.magna.api.masterlocadora.entity.ClienteEntity;
import br.com.magna.api.masterlocadora.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<ClienteDto> buscaEspecifica(Pageable pageable) {
		Page<ClienteEntity> cliente = clienteRepository.findAll(pageable);
		return cliente.map(item -> modelMapper.map(item, ClienteDto.class));
	}

	public ClienteDto getLogin(String cpf) throws NotFoundException {
		ClienteEntity cliente = clienteRepository.findByCpf(cpf);
		ClienteDto usuarioDto = converterParaDto(cliente);

		if (usuarioDto == null) {
			throw new NotFoundException();
		}
		return usuarioDto;
	}

	public ClienteDto SalvandoClienteDto(ClienteDto clienteDtoSave) throws Exception {
		try {
			if (ValidandoCpf(clienteDtoSave.getCpf())) {
				ClienteEntity cliente = modelMapper.map(clienteDtoSave, ClienteEntity.class);
				@SuppressWarnings("unused")
				ClienteEntity clienteEntity = clienteRepository.save(cliente);
				return clienteDtoSave;
			} else {
				System.out.println("Cpf Invalido");
			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public ClienteDto alterarClienteDto(ClienteDto clienteDto) {
		try {
			ClienteEntity cliente = clienteRepository.save(converterParaEntity(clienteDto));
			ClienteDto clienteDtoSalvo = converterParaDto(cliente);
			return clienteDtoSalvo;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return clienteDto;
	}

	public ClienteDto update(String cpf, ClienteDto clienteDto) throws NotFoundException {

		ClienteEntity cliente = clienteRepository.findByCpf(cpf);
		ClienteDto clienteDtoAntigo = converterParaDto(cliente);
		BeanUtils.copyProperties(clienteDto, clienteDtoAntigo, "cliente");
		ClienteEntity convertEntity = converterParaEntity(clienteDto);
		convertEntity.setId(cliente.getId());
		ClienteEntity clienteAtualizado = clienteRepository.save(convertEntity);
		return converterParaDto(clienteAtualizado);
	}

	public static boolean ValidandoCpf(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		try {
			cpfValidator.assertValid(cpf);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private ClienteDto converterParaDto(ClienteEntity cliente) {
		return modelMapper.map(cliente, ClienteDto.class);
	}

	private ClienteEntity converterParaEntity(ClienteDto clienteDto) {
		return modelMapper.map(clienteDto, ClienteEntity.class);
	}

}
