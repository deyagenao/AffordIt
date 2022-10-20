package com.deyaniragenao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.User;

/**
 * Repository class for interacting with the User Table in the database. Contains one custom method. 
 * @author deyaniragenao
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{

	/**
	 * Accepts a string email and returns the User record with the corresponding email
	 * @param email
	 * @return user
	 */
	User findByEmail(String email);

}
