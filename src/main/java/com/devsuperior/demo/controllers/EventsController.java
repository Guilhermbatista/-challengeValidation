package com.devsuperior.demo.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.services.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/events")
public class EventsController {
	
	@Autowired
	private EventService service;
	
	@GetMapping()
	public ResponseEntity<Page<EventDTO>> findAllPaged(Pageable pageable) {
		return ResponseEntity.ok().body(service.findAllPaged(pageable));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EventDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN' )")
	public ResponseEntity<EventDTO> insert(@Valid @RequestBody EventDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/{id}")
	public ResponseEntity<EventDTO> update(@PathVariable Long id, @Valid @RequestBody EventDTO dto) {
		return ResponseEntity.ok().body(service.update(id, dto));
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
