package com.deyaniragenao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Account;

/**
 * Repository class for interacting with the Account Table in the database.
 * @author deyaniragenao
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String>{


}
