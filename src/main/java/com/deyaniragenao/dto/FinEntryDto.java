package com.deyaniragenao.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinEntryDto {
	
	@NotEmpty(message = "Please enter a name to reference your entry.")
	String name;
	@NotNull(message = "Please enter an amount.")
	BigDecimal amount;
	@NotNull(message = "Please choose a category.")
	Integer categoryId;
	@NotEmpty
	String frequency;
}
