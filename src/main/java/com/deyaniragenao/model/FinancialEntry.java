package com.deyaniragenao.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class FinancialEntry {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private BigDecimal amount;
	private String description;
	@Column(nullable = false)
	private String frequency;
	@Column(nullable = false)
	private Date date;
	
	
}
