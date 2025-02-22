package com.devsuperior.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import com.devsuperior.demo.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CityDTO> findAllPaged(Pageable pageable) {
		Page<City> list = repository.findAll(pageable);
		return list.map(x -> new CityDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CityDTO findById(Long id) {
		Optional<City> obj = repository.findById(id);
		City entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new CityDTO(entity);
	}
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CityDTO(entity);
	}

	
	@Transactional
	public CityDTO update(Long id, CityDTO dto) {
		try {
			City city = repository.getReferenceById(id);
			city.setName(dto.getName());
			city = repository.save(city);
			return new CityDTO(city);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			repository.deleteById(id);
		}catch (DataIntegrityViolationException e){
			throw new DatabaseException("Integrity violation");

		}
	}
	
	
}
