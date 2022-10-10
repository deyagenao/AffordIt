package com.deyaniragenao.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "expenses")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Expense extends FinancialEntry {

	@ManyToOne
	@JoinColumn(name = "acc_id", nullable = false)
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "cat_id", nullable = false)
	private Category category;
	
	
	
}
