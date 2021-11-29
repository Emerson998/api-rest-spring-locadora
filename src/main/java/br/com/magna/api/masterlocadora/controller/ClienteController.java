package br.com.magna.api.masterlocadora.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magna.api.masterlocadora.dto.ClienteDto;
import br.com.magna.api.masterlocadora.repository.ClienteRepository;
import br.com.magna.api.masterlocadora.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
@RequestMapping("/cliente")

public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteRepository clienteRepository;

	@ApiOperation(value = "Retorna Todos os Clientes")
	@GetMapping
	public ResponseEntity<Page<ClienteDto>> list(Pageable pageable) {

		return ResponseEntity.ok(clienteService.buscaEspecifica(pageable));
	}

	@ApiOperation(value = "Retorna Todos os Clientes Por Cpf")
	@GetMapping("/{cpf}")
	public ResponseEntity<ClienteDto> listLogin(@PathVariable String cpf) throws NotFoundException {
		try {
			return ResponseEntity.ok(clienteService.getLogin(cpf));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Cliente não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Salva os Clientes")
	@PostMapping
	public ResponseEntity<ClienteDto> post(@RequestBody ClienteDto clienteDto) throws Exception {
		try {
			ClienteDto resultado = clienteService.SalvandoClienteDto(clienteDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Cliente não cadastrado");
			return ResponseEntity.noContent().build();
		}

	}

	@ApiOperation(value = "Atualiza os Clientes")
	@PutMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ClienteDto> put(@PathVariable String cpf, @RequestBody ClienteDto clienteDto)
			throws Exception {
		try {
			ClienteDto clienteDtoAltera = clienteService.update(cpf, clienteDto);
			return ResponseEntity.ok(clienteDtoAltera);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Cliente não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Deleta os Clientes")
	@DeleteMapping("/{id}")
	public ResponseEntity<ClienteDto> remover(@PathVariable Long id) {
		clienteRepository.deleteById(id);
		return ResponseEntity.ok().build();

	}

}
