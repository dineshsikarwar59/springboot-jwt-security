package com.dinesh.jwtSecurity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthRequestDTO {
	
	private String username;
	private String password;

}