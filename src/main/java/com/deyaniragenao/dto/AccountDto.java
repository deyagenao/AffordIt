package com.deyaniragenao.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * AccountDto is Data Transfer Object class. Used for validating the creation of a new account 
 * from the user account form. 
 * @author deyaniragenao
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto {
	
	@NotEmpty(message = "Please enter a name for your new account")
	String name;
	String description;
	
}
