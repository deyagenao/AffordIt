package com.deyaniragenao.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * An abstract class that is also a MappedSuperclass. There is no corresponding table in the database. 
 * Instead, this class is a super class for financial entry objects- specificially expense and income
 * objects.  
 * @author deyaniragenao
 *
 */
@MappedSuperclass
@Getter
@Setter
public abstract class FinancialEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private BigDecimal amount;
	@Column(nullable = false)
	private BigDecimal monthlyAmount;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FinancialEntryFrequency frequency;
	@Column(nullable = false)
	LocalDateTime date = LocalDateTime.now();
	
	/**
	 * Uses the specified amount and financial frequency to calculate the monnthly amount 
	 * @return monthlyAmount
	 */
	public BigDecimal calculateMonthlyAmount() {
		BigDecimal monthlyAmount = this.amount;
		switch(this.frequency) {
			case WEEKLY:
				monthlyAmount = monthlyAmount.multiply(new BigDecimal("4"));
				break;
			case BIWEEKLY:
				monthlyAmount = monthlyAmount.multiply(new BigDecimal("2"));
				break;
			case MONTHLY: 
				break;
			case ANNUALLY:
				monthlyAmount = monthlyAmount.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
		}
		
		return monthlyAmount;
	}
	
	/**
	 * To string method that uses the class name of the object that calls the method. 
	 * @return string
	 */
	@Override
	public String toString() {
		return this.getClass().getName() + " [id=" + id + ", name=" + name + ", amount=" + amount + ", monthlyAmount=" + monthlyAmount + ", frequency="
				+ frequency + ", date=" + date + "]";
	}
	
}
