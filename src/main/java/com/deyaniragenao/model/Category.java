package com.deyaniragenao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Category is the model class that represents the different types of expenses and sources of income.
 * @author deyaniragenao
 *
 */
@Entity
@Table(name = "categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(nullable = false)
	String name;
	@Column(nullable = false)
	String type;
	String description;
	
	/**
	 * Category constructor with name and type parameters
	 * @param name
	 * @param type
	 */
	public Category(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * Category constructor with name, type, and description parameters
	 * @param name
	 * @param type
	 * @param desc
	 */
	public Category(String name, String type, String desc) {
		this.name = name;
		this.type = type;
		this.description = desc;
	}
	
}
