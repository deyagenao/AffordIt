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

@Service
@Slf4j
public class DiscretionaryService {
	
	@Autowired
	DiscretionaryRepository discRepository;
	@Autowired
	AccountRepository accountRepository;
	
	public void saveNewDiscretionary(Discretionary disc, User user, String accId) {
		log.info("made it to the service class");
		disc.setUser(user);
		user.addDiscretionary(disc);
		
		Optional<Account> accData = accountRepository.findById(accId);
		accData.get().addDiscretionary(disc);
		
		discRepository.save(disc);
	}
	
	public void deleteDiscretionary(Long id) {
		discRepository.deleteById(id);
	}
}
