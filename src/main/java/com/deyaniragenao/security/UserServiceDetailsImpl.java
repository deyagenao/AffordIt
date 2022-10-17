package com.deyaniragenao.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deyaniragenao.model.User;
import com.deyaniragenao.service.UserService;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {

	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		User user = userService.findUserByEmail(email);
		
		if(user != null) {
			return new org.springframework.security.core.userdetails.User(
					user.getEmail(),
					user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
					
		} else {
			throw new UsernameNotFoundException("Invalid email or password");
		}
	}
}
