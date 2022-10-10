package com.deyaniragenao.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;
	@Column(nullable = false)
	String firstName;
	@Column(nullable = false)
	String lastName;
	@Column(nullable = false, unique = true)
	String email;
	@Column(nullable = false)
	String password;
	@Column(nullable = false)
	Date dateCreated;
	Date dateLastUpdated;
	
	@ManyToMany
	@JoinTable(
			name = "users_accounts",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	Set<Account> accounts;
	
	@OneToMany(mappedBy = "user")
	Set<Expense> expenses;
	
	@OneToMany(mappedBy = "user")
	Set<Income> incomes;
	
	@OneToMany(mappedBy = "user")
	Set<Discretionary> discretionaryItems;

	public User(String firstName, String lastName, String email, String password, Date dateCreated,
			Date dateLastUpdated) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
	}
	
	
}
