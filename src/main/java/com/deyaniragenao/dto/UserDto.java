package com.deyaniragenao.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * User Data Transfer Object class used for validating the creation of a new user. 
 * @author deyaniragenao
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
	
	@NotEmpty(message = "Please enter your first name")
	String firstName; 
	@NotEmpty(message = "Please enter your last name")
	String lastName;
	@NotEmpty(message = "Please enter an email address")
	@Email(message = "Please enter a valid email address")
	String email;
	@NotEmpty(message = "Please enter your password")
	String password;
	@NotEmpty(message = "Please confirm your password")
	String passwordConf;
	
}
