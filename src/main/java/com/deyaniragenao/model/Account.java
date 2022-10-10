package com.deyaniragenao.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@ToString
// Equals and Hashcode 
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;
	@Column(nullable = false)
	String name;
	String description;
	@Column(nullable = false)
	Date dateCreated;
	Date dateLastUpdated;
	
	@ManyToMany(mappedBy = "accounts")
	Set<User> accountUsers;
	
	@OneToMany(mappedBy = "account")
	Set<Expense> expenses;
	
	@OneToMany(mappedBy = "account")
	Set<Income> incomes;
	
	@OneToMany(mappedBy = "account")
	Set<Discretionary> discretionaryItems;

	public Account(String name, String description, Date dateCreated, Date dateLastUpdated, Set<User> accountUsers) {
		this.name = name;
		this.description = description;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.accountUsers = accountUsers;
	}
	
	
}
