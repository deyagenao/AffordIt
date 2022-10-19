package com.deyaniragenao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.deyaniragenao.model.Account;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, String>{


}
