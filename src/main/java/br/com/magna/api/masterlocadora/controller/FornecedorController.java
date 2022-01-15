package br.com.magna.api.masterlocadora.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.magna.api.masterlocadora.dto.FornecedorDto;
import br.com.magna.api.masterlocadora.service.FornecedorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/fornecedor")

public class FornecedorController {

	@Autowired
	private FornecedorService fornecedorService;

	@ApiOperation(value = "Busca Todos os Fornecedores")
	@GetMapping
	@Cacheable(value = "listaDeFornecedores")
	public Page<FornecedorDto> page(Pageable pageable) {
		try {
			return fornecedorService.page(pageable);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "Busca individual pelo Cnpj")
	@GetMapping("/{cnpj}")
	@CacheEvict(value = "listaDeFornecedores", allEntries = true)
	public ResponseEntity<FornecedorDto> search(@PathVariable String cnpj) {
		try {
			return ResponseEntity.ok(fornecedorService.search(cnpj));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Salva o Fornecedor")
	@PostMapping
	@CacheEvict(value = "listaDeFornecedores", allEntries = true)
	public ResponseEntity<FornecedorDto> save(@RequestBody FornecedorDto fornecedorDto) {
		try {
			FornecedorDto resultado = fornecedorService.save(fornecedorDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
		} catch (Exception ex) {
			ex.getMessage();
			return ResponseEntity.noContent().build();
		}

	}

	@ApiOperation(value = "Atualiza Fornecedor")
	@PutMapping("/{cnpj}")
	@Transactional
	@CacheEvict(value = "listaDeFornecedores", allEntries = true)
	public ResponseEntity<FornecedorDto> update(@PathVariable String cnpj, @RequestBody FornecedorDto fornecedorDto)
			throws Exception {
		try {
			FornecedorDto fornecedorDtoAltera = fornecedorService.update(cnpj, fornecedorDto);
			return ResponseEntity.ok(fornecedorDtoAltera);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Remove o Fornecedor")
	@DeleteMapping("/{cnpj}")
	@Transactional
	@CacheEvict(value = "listaDeFornecedores", allEntries = true)
	public ResponseEntity<FornecedorDto> delete(@PathVariable String cnpj) {
		try {
			fornecedorService.delete(cnpj);
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return null;
	}
}
