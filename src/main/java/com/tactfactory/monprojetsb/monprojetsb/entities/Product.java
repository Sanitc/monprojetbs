package com.tactfactory.monprojetsb.monprojetsb.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Product {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private float price;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public Product(long id, String name, float price){
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public Product() {
		
	}
}