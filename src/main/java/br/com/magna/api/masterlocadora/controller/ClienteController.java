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
import br.com.magna.api.masterlocadora.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Busca Todos os Clientes")
	@GetMapping
	public Page<ClienteDto> page(Pageable pageable) {
		try {
			return clienteService.page(pageable);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return page(pageable);
	}

	@ApiOperation(value = "Busca individual pelo Cpf")
	@GetMapping("/{cpf}")
	public ResponseEntity<ClienteDto> search(@PathVariable String cpf) {
		try {
			return ResponseEntity.ok(clienteService.search(cpf));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Cadastra o  Cliente")
	@PostMapping
	public ResponseEntity<ClienteDto> save(@RequestBody ClienteDto clienteDto) {
		try {
			ClienteDto resultado = clienteService.save(clienteDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
		} catch (Exception ex) {
			ex.getMessage();
			return ResponseEntity.noContent().build();
		}

	}

	@ApiOperation(value = "Atualiza o Cliente")
	@PutMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ClienteDto> update(@PathVariable String cpf, @RequestBody ClienteDto clienteDto)
			throws Exception {
		try {
			ClienteDto clienteDtoAltera = clienteService.update(cpf, clienteDto);
			return ResponseEntity.ok(clienteDtoAltera);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Remove o Cliente")
	@DeleteMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ClienteDto> delete(@PathVariable String cpf) {
		try {
			clienteService.delete(cpf);
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
}