package com.deyaniragenao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.deyaniragenao.model.Account;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, String>{

////	@Query("SELECT a FROM Account WHERE a.")
////	@Query(value = "SELECT * FROM author WHERE id = ?1",
////			nativeQuery = true) -- another acceptable format 
//	@Query("SELECT u FROM User u JOIN FETCH u.accounts "
////			+ "Account a "
////			+ "JOIN users_accounts ua ON a.id = ua.acc_id "
//			+ "WHERE u.id = ?1")
//	Set<Account> findAccountsByUserId(String id);
}
