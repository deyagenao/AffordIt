package com.deyaniragenao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Discretionary;
import com.deyaniragenao.model.User;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.DiscretionaryRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * DiscretionaryService is a service class that implements customized methods and business 
 * logic for interacting with discretionary-related data. 
 * @author deyaniragenao
 *
 */
@Service
@Slf4j
public class DiscretionaryService {
	
	@Autowired
	DiscretionaryRepository discRepository;
	@Autowired
	AccountRepository accountRepository;
	
	/**
	 * Accepts a new disc, user object and accId string. Discretionary object is added the sets of discretionary
	 * items in the user and account objects. Then discretionary object is persisted to the database. 
	 * @param disc
	 * @param user
	 * @param accId
	 */
	public void saveNewDiscretionary(Discretionary disc, User user, String accId) {
		log.info("made it to the service class");
		disc.setUser(user);
		user.addDiscretionary(disc);
		
		Optional<Account> accData = accountRepository.findById(accId);
		accData.get().addDiscretionary(disc);
		
		discRepository.save(disc);
	}
	
	/**
	 * Accepts a Long id and deletes the discretionary record with the corresponding id. 
	 * @param id
	 */
	public void deleteDiscretionary(Long id) {
		discRepository.deleteById(id);
	}
}
