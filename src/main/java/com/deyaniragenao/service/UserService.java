package com.deyaniragenao.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.User;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired 
	AccountRepository accountRepository;
	
	public void saveNewUser(User user) {
		userRepository.save(user);
	}
	
	public User addNewAccount(String id, Account acc) {
		Optional<User> userData = userRepository.findById(id);
		// Optional<Account> accData = accountRepository.findById(acc.getId());
		User user = userData.get();
		// Account account = accData.get();
		
		user.addAccount(acc);
		
		userRepository.save(user);
		// accountRepository.save(account);
		
		return user;
	}
	
	public Set<Account> findAccountsByUserId(String id) {
		return accountRepository.findAccountsByUserId(id);
	}
}
