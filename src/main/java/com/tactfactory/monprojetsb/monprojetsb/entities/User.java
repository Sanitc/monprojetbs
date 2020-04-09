package com.tactfactory.monprojetsb.monprojetsb.entities;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class User {
	
	@Id
	@GeneratedValue
	private long id;
	private String firstname;
	private String lastname;
	@OneToMany
	private List<Product>products;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public 	User(long id, String firstname, String lastname, List<Product>products) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.products = products;
	}
	
}
