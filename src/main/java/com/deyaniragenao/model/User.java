package com.deyaniragenao.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
 * User Model class represents user objects and is used to create the User Table. 
 * @author deyaniragenao
 *
 */
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"accounts", "expenses", "incomes", "discretionaryItems"})
public class User {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(updatable = false, insertable = false)
	String id;

	@Column(nullable = false)
	String firstName;
	@Column(nullable = false)
	String lastName;
	@Column(nullable = false, unique = true)
	String email;
	@Column(nullable = false)
	String password;
	@Column(nullable = false)
	LocalDateTime dateCreated = LocalDateTime.now();
	LocalDateTime dateLastUpdated;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "users_accounts",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	Set<Account> accounts = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Expense> expenses = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Income> incomes = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Discretionary> discretionaryItems = new HashSet<>();

	/**
	 * User Constructor with firstName, lastName email and password parameters 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 */
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Helper method for adding account to user's set of accounts 
	 * @param account
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
		account.getAccountUsers().add(this);
	}
	
	/** 
	 * Helper method for deleting an account from user's set of accounts 
	 * @param account
	 */
	public void removeAccount(Account account) {
		this.accounts.remove(account);
		account.getAccountUsers().remove(this);
	}
	
	/**
	 * helper method for adding an expense to a user's set of expenses
	 * @param expense
	 */
	public void addExpense(Expense expense) {
		this.getExpenses().add(expense);
		expense.setUser(this);
	}
	
	/**
	 * helper method for removing an expense from user's set of expenses 
	 * @param expense
	 */
	public void removeExpense(Expense expense) {
		this.getExpenses().remove(expense);
	}
	
	/**
	 * helper method for adding an income object to user's set of income objects
	 * @param income
	 */
	public void addIncome(Income income) {
		this.getIncomes().add(income);
		income.setUser(this);
	}
	
	/**
	 * helper method for removing an income object from a user 
	 * @param income
	 */
	public void removeIncome(Income income) {
		this.getIncomes().remove(income);
	}
	
	/**
	 * helper method for adding a discretionary item to user's set of items
	 * @param discretionary
	 */
	public void addDiscretionary(Discretionary discretionary) {
		this.getDiscretionaryItems().add(discretionary);
		discretionary.setUser(this);
	}
	
	/**
	 * Helper method for removing a discretionary item from user 
	 * @param discretionary
	 */
	public void removeDiscretionary(Discretionary discretionary) {
		this.getDiscretionaryItems().remove(discretionary);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", dateCreated=" + dateCreated + ", dateLastUpdated=" + dateLastUpdated
				+ "]";
	}
	
	

}
