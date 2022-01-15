package br.com.magna.api.masterlocadora.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.magna.api.masterlocadora.dto.LocadoraDto;
import br.com.magna.api.masterlocadora.service.LocadoraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/locadora")

public class LocadoraController {

	@Autowired
	private LocadoraService locadoraService;

	@ApiOperation(value = "Retorna Todos as Locadoras")
	@GetMapping
	@Cacheable(value = "listaDeLocadoras")
	public Page<LocadoraDto> page(Pageable pageable) {
		try {
			return locadoraService.page(pageable);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "Retorna Todos os Locadoras Por Cnpj")
	@GetMapping("/{cnpj}")
	@CacheEvict(value = "listaDeLocadoras", allEntries = true)
	public ResponseEntity<LocadoraDto> search(@PathVariable String cnpj) {
		try {
			return ResponseEntity.ok(locadoraService.search(cnpj));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Salva as Locadoras")
	@PostMapping
	@CacheEvict(value = "listaDeLocadoras", allEntries = true)
	public ResponseEntity<LocadoraDto> save(@RequestBody LocadoraDto locadoraDto) {
		try {
			LocadoraDto resultado = locadoraService.save(locadoraDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
		} catch (Exception ex) {
			ex.getMessage();
			return ResponseEntity.noContent().build();
		}

	}

	@ApiOperation(value = "Altera Locadoras")
	@PutMapping("/{cnpj}")
	@Transactional
	@CacheEvict(value = "listaDeLocadoras", allEntries = true)
	public ResponseEntity<LocadoraDto> update(@PathVariable String cnpj, @RequestBody LocadoraDto locadoraDto) {
		try {
			LocadoraDto locadoraDtoUpdate = locadoraService.update(cnpj, locadoraDto);
			return ResponseEntity.ok(locadoraDtoUpdate);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Deleta Locadoras")
	@DeleteMapping("/{cnpj}")
	@Transactional
	@CacheEvict(value = "listaDeLocadoras", allEntries = true)
	public ResponseEntity<LocadoraDto> delete(@PathVariable String cnpj) {
		try {
			locadoraService.delete(cnpj);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
}
