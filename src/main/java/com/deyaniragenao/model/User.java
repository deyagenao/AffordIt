package com.deyaniragenao.model;

import java.time.LocalDateTime;
import java.util.HashSet;
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

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"accounts"})
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
	Set<Account> accounts = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Expense> expenses = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Income> incomes = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Discretionary> discretionaryItems = new HashSet<>();

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	// p.33 of Spring Data persistence 
	public void addAccount(Account account) {
		this.accounts.add(account);
		account.getAccountUsers().add(this);
	}
	
	public void removeAccount(Account account) {
		this.accounts.remove(account);
		account.getAccountUsers().remove(this);
	}
	
	//OneToMany Helper methods 
	public void addExpense(Expense expense) {
		this.getExpenses().add(expense);
		expense.setUser(this);
	}
	
	public void removeExpense(Expense expense) {
		this.getExpenses().remove(expense);
	}
	
	public void addIncome(Income income) {
		this.getIncomes().add(income);
		income.setUser(this);
	}
	
	public void removeIncome(Income income) {
		this.getIncomes().remove(income);
	}
	
	public void addDiscretionary(Discretionary discretionary) {
		this.getDiscretionaryItems().add(discretionary);
		discretionary.setUser(this);
	}
	
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
