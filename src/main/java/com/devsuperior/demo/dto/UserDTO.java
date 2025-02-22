package com.devsuperior.demo.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.devsuperior.demo.entities.User;


public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	
	Set<RoleDTO>roles= new HashSet<>();

	public UserDTO() {
	}
	public UserDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public UserDTO(User dto) {
		id = dto.getId();
		firstName = dto.getFirstName();
		lastName = dto.getLastName();
		email = dto.getEmail();
		dto.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}


	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	
}
