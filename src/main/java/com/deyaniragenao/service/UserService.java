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
 * User service class handles the business logic for creating, reading, updating and persisting 
 * user objects. 
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
	
	/**
	 * Method accepts a UserDto object. Creates a new user object and sets its values with the userDto 
	 * values. Password is encoded before the user object is persisted to the database. 
	 * @param userDto
	 */
	public void saveNewUser(UserDto userDto) {
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		userRepository.save(user);
	}
	
	/**
	 * Method accepts a string representing a user email address. Returns the user object with the 
	 * matching email address
	 * @param email
	 * @return user
	 */
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * Method retrieves the user from the database using user id, then adds an account to the user's
	 * set of accounts. Then updates the user in the database. 
	 * @param id
	 * @param acc
	 * @return
	 */
	public User addNewAccount(String id, Account acc) {
		Optional<User> userData = userRepository.findById(id);
		User user = userData.get();
		
		user.addAccount(acc);
		
		userRepository.save(user);
		
		return user;
	}	
}
