package com.dinesh.jwtSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.jwtSecurity.domain.User;
import com.dinesh.jwtSecurity.dto.AuthRequestDTO;
import com.dinesh.jwtSecurity.dto.AuthResponseDTO;
import com.dinesh.jwtSecurity.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public AuthResponseDTO login(@RequestBody AuthRequestDTO authRequestDTO) {
		return userService.login(authRequestDTO);
	}
	
	@PostMapping("/update")
	public User update(@RequestBody User user) {
		return userService.update(user);
	}
	
	@GetMapping("all")
	public List<User> getUsers(){
		return userService.getUsers();
	}

}
