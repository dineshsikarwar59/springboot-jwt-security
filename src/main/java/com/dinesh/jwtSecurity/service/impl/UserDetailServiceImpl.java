package com.dinesh.jwtSecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dinesh.jwtSecurity.domain.MyUserDetail;
import com.dinesh.jwtSecurity.domain.User;
import com.dinesh.jwtSecurity.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRespository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=  userRespository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("User Not Found:"+username);
		}
		return new MyUserDetail(user);
	}
}
