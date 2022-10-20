package com.deyaniragenao.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * FinEntryDto is Data Transfer Object class. Used for validating the creation of a new expense  
 * or income record from the respective forms. 
 * @author deyaniragenao
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinEntryDto {
	
	@NotEmpty(message = "Please enter a name to reference your entry.")
	String name;
	@NotNull(message = "Please enter a valid amount.")
	@Digits(fraction = 2, integer = 10)
	BigDecimal amount;
	@NotNull(message = "Please choose a category.")
	@Digits(fraction = 0, integer = 2)
	Integer categoryId;
	@NotEmpty
	String frequency;
}
