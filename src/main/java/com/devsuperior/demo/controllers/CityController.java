package com.devsuperior.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.services.CityService;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

	@Autowired
	private CityService service;

	@GetMapping()
	public ResponseEntity<Page<CityDTO>> findAllPaged(Pageable pageable) {
		return ResponseEntity.ok().body(service.findAllPaged(pageable));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CityDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping()
	@PreAuthorize("hasAnyRole('ROLE_OPERATOR','ROLE_ADMIN' )")
	public ResponseEntity<CityDTO> insert(@RequestBody @Valid CityDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/{id}")
	public ResponseEntity<CityDTO> update(@PathVariable Long id, @Valid @RequestBody CityDTO dto) {
		return ResponseEntity.ok().body(service.update(id, dto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
