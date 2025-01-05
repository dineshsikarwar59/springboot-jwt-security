package com.dinesh.jwtSecurity.service;

import java.util.List;

import com.dinesh.jwtSecurity.domain.User;
import com.dinesh.jwtSecurity.dto.AuthRequestDTO;
import com.dinesh.jwtSecurity.dto.AuthResponseDTO;

public interface UserService {
	
	public User register(User user);
	
	public AuthResponseDTO login(AuthRequestDTO authRequestDTO);
	
	public User update(User user); 
	
	public List<User> getUsers();
	

}
