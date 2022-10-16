package com.deyaniragenao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import com.deyaniragenao.model.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String>{

	User findByEmail(String email);

}
