package com.spring.bikesshop.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity @Data
public class Bike {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String marca;
	
	@ManyToOne
	@JoinColumn(name="idShop")
	private Shop shop;
	
	public Bike () {}

	public Bike(String name, String marca, Shop shop) {
		this.name = name;
		this.marca = marca;
		this.shop = shop;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, marca, name, shop);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bike other = (Bike) obj;
		return Objects.equals(id, other.id) && Objects.equals(marca, other.marca) && Objects.equals(name, other.name)
				&& Objects.equals(shop, other.shop);
	}

	@Override
	public String toString() {
		return "Bike [id=" + id + ", name=" + name + ", marca=" + marca + ", shop=" + shop + "]";
	}

}
