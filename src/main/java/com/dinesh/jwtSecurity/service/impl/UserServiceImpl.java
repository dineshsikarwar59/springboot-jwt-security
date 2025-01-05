package com.dinesh.jwtSecurity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dinesh.jwtSecurity.domain.User;
import com.dinesh.jwtSecurity.dto.AuthRequestDTO;
import com.dinesh.jwtSecurity.dto.AuthResponseDTO;
import com.dinesh.jwtSecurity.repository.UserRepository;
import com.dinesh.jwtSecurity.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRespository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRespository.save(user);
		return user;
	}

	@Override
	public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(authRequestDTO.getUsername());
			AuthResponseDTO authResponseDTO = new AuthResponseDTO();
			authResponseDTO.setToken(token);
			authResponseDTO.setExpiration("30 Minute");
			return authResponseDTO;
		} else {
			throw new UsernameNotFoundException("Invalid user request!");
		}
	}

	@Override
	public User update(User user) {
		return userRespository.save(user);
	}

	@Override
	public List<User> getUsers() {
		return userRespository.findAll();
	}

}
