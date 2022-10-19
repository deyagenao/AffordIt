package com.deyaniragenao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deyaniragenao.dto.UserDto;
import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.User;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.UserRepository;

/**
 * 
 * @author deyaniragenao
 *
 */
@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired 
	AccountRepository accountRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void saveNewUser(UserDto userDto) {
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		userRepository.save(user);
	}
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	// Account
	public User addNewAccount(String id, Account acc) {
		Optional<User> userData = userRepository.findById(id);
		User user = userData.get();
		
		user.addAccount(acc);
		
		userRepository.save(user);
		
		return user;
	}	
}
