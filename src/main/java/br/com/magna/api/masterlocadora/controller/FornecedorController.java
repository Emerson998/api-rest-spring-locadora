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
import br.com.magna.api.masterlocadora.dto.FornecedorDto;
import br.com.magna.api.masterlocadora.repository.FornecedorRepository;
import br.com.magna.api.masterlocadora.service.FornecedorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/fornecedor")

public class FornecedorController {

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@ApiOperation(value = "Retorna Todos os Fornecedores")
	@GetMapping
	public ResponseEntity<Page<FornecedorDto>> list(Pageable pageable) {

		return ResponseEntity.ok(fornecedorService.buscaEspecifica(pageable));
	}

	@ApiOperation(value = "Retorna Todos os Fornecedores Por Cnpj")
	@GetMapping("/{cnpj}")
	public ResponseEntity<FornecedorDto> listCnpj(@PathVariable String cnpj) throws NotFoundException {
		try {
			return ResponseEntity.ok(fornecedorService.getCnpj(cnpj));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Fornecedor não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Cadastra Fornecedor")
	@PostMapping
	public ResponseEntity<FornecedorDto> post(@RequestBody FornecedorDto fornecedorDto) throws Exception {
		try {
			FornecedorDto resultado = fornecedorService.salvarFornecedorDto(fornecedorDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Fornecedor não cadastrado");
			return ResponseEntity.noContent().build();
		}

	}

	@ApiOperation(value = "Atualiza Fornecedor")
	@PutMapping("/{cnpj}")
	@Transactional
	public ResponseEntity<FornecedorDto> put(@PathVariable String cnpj, @RequestBody FornecedorDto fornecedorDto)
			throws Exception {
		try {
			FornecedorDto fornecedorDtoAltera = fornecedorService.update(cnpj, fornecedorDto);
			return ResponseEntity.ok(fornecedorDtoAltera);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Fornecedor não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	// Deletando usuario
	@DeleteMapping("/{cnpj}")
	@Transactional
	public ResponseEntity<FornecedorDto> delete(@PathVariable String cnpj) throws NotFoundException {
		try {
			fornecedorService.delete(cnpj);
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Fornecedor não encontrado");
			return ResponseEntity.notFound().build();
		}
	}
}
