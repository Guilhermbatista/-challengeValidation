package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.UserDTO;
import com.devsuperior.demo.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

	private static final long serialVersionUID = 1L;
	private String password;

	UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
