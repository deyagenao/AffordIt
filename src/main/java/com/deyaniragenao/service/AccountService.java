package com.deyaniragenao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.repository.AccountRepository;

/**
 * /**
 * AccountService is a service class the logic for interacting with account data. 
 * @author deyaniragenao
 *
 */
@Service
public class AccountService {
	
	AccountRepository accountRepository; 
	
	/**
	 * Accepts a string id and then returns the account with the corresponding id
	 * @return Optional<Account>
	 */
	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}
