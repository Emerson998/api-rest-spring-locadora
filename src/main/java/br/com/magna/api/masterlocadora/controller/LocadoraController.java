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
import br.com.magna.api.masterlocadora.dto.LocadoraDto;
import br.com.magna.api.masterlocadora.repository.LocadoraRepository;
import br.com.magna.api.masterlocadora.service.LocadoraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/locadora")

public class LocadoraController {

	@Autowired
	private LocadoraService locadoraService;

	@Autowired
	private LocadoraRepository clienteRepository;
    
	@ApiOperation(value = "Retorna Todos os Clientes")
	@GetMapping
	public ResponseEntity<Page<LocadoraDto>> list(Pageable pageable) {

		return ResponseEntity.ok(locadoraService.buscaEspecifica(pageable));
	}

	@ApiOperation(value = "Retorna Todos os Clientes Por Cnpj")
	@GetMapping("/{cnpj}")
	public ResponseEntity<LocadoraDto> listCnpj(@PathVariable String cnpj) throws NotFoundException {
		try {
			return ResponseEntity.ok(locadoraService.getCnpj(cnpj));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Locadora não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Cadastra os Clientes")
	@PostMapping
    public ResponseEntity<LocadoraDto> post(@RequestBody LocadoraDto locadoraDto) throws Exception {
		try {
			LocadoraDto resultado = locadoraService.salvarLocadoraDto(locadoraDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Locadora não cadastrada");
			return ResponseEntity.noContent().build();
		}

	}

	@ApiOperation(value = "Altera Clientes")
	@PutMapping("/{cnpj}")
	@Transactional
	public ResponseEntity<LocadoraDto> put(@PathVariable String cnpj, @RequestBody LocadoraDto locadoraDto)
			throws Exception {
		try {
			LocadoraDto locadoraDtoAltera = locadoraService.update(cnpj, locadoraDto);
			return ResponseEntity.ok(locadoraDtoAltera);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Locadora não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Deleta Clientes")
	@DeleteMapping("/{id}")
	public ResponseEntity<ClienteDto> remover(@PathVariable Long id) {
		clienteRepository.deleteById(id);
		return ResponseEntity.ok().build();

	}

}
