package com.deyaniragenao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Discretionary is the model class for creating the discretionary items table and discretionary objects 
 * @author deyaniragenao
 *
 */
@Entity
@Table(name = "discretionary_items")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Discretionary implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	BigDecimal amount;
	String description;
	@Column(nullable = false)
	LocalDateTime dateCreated = LocalDateTime.now();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acc_id", nullable = false)
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	/**
	 * Discretionary constructor with name, amount and description parameters 
	 * @param name
	 * @param amount
	 * @param description
	 */
	public Discretionary(String name, BigDecimal amount, String description) {
		this.name = name;
		this.amount = amount;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Discretionary [id=" + id + ", name=" + name +", amount=" + amount + ", description=" + description + ", date=" + dateCreated
				+ "]";
	}	
	
}
