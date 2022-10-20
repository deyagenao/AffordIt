package com.deyaniragenao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Account is a model class used to create account objects. 
 * @author deyaniragenao
 *
 */
@Entity
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"accountUsers", "expenses", "incomes", "discretionaryItems"})
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(updatable = false, insertable = false)
	String id;
	@Column(nullable = false)
	String name;
	String description;
	@Column(nullable = false)
	LocalDateTime dateCreated = LocalDateTime.now();
	LocalDateTime dateLastUpdated;
	
	@ManyToMany(mappedBy = "accounts")
	Set<User> accountUsers = new HashSet<>();
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Expense> expenses = new HashSet<>();;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Income> incomes = new HashSet<>();;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Discretionary> discretionaryItems = new HashSet<>();;

	/**
	 * Account constructor with name parameter
	 * @param name
	 */
	public Account(String name) {
		this.name = name;
	}
	
	/**
	 * Account constructor with name and description parameters
	 * @param name
	 * @param description
	 */
	public Account(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Helper method for adding new expense to account 
	 * @param expense
	 */
	public void addExpense(Expense expense) {
		this.getExpenses().add(expense);
		expense.setAccount(this);
	}
	
	/**
	 * Helper method for removing expense from account
	 * @param expense
	 */
	public void removeExpense(Expense expense) {
		this.getExpenses().remove(expense);
	}
	
	/**
	 * Helper method for adding income to an account
	 * @param income
	 */
	public void addIncome(Income income) {
		this.getIncomes().add(income);
		income.setAccount(this);
	}
	/**
	 * Helper method for removing income from account
	 * @param income
	 */
	public void removeIncome(Income income) {
		this.getIncomes().remove(income);
	}
	/**
	 * Helper method for adding a new discretionary item to account
	 * @param discretionary
	 */
	public void addDiscretionary(Discretionary discretionary) {
		this.getDiscretionaryItems().add(discretionary);
		discretionary.setAccount(this);
	}
	/**
	 * Helper method for deleting discretionary from account 
	 * @param discretionary
	 */
	public void removeDiscretionary(Discretionary discretionary) {
		this.getDiscretionaryItems().remove(discretionary);
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", description=" + description + ", dateCreated=" + dateCreated
				+ ", dateLastUpdated=" + dateLastUpdated + "]";
	}
}
