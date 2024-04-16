package com.spring.bikesshop.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity @Data
public class Bike {
	
	@Id
	@GeneratedValue
	private Long id;
	@NotNull(message = "Name is required")
    @Size(min = 3, message = "Title must be at least 3 characters long")
	private String name;
	@NotNull(message = "Brand is required")
	private String brand;
	
	@ManyToOne
	@JoinColumn(name="idShop")
	private Shop shop;
	
	public Bike () {}

	public Bike(String name, String brand, Shop shop) {
		this.name = name;
		this.brand = brand;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, brand, name, shop);
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
		return Objects.equals(id, other.id) && Objects.equals(brand, other.brand) && Objects.equals(name, other.name)
				&& Objects.equals(shop, other.shop);
	}

	@Override
	public String toString() {
		return "Bike [id=" + id + ", name=" + name + ", brand=" + brand + ", shop=" + shop + "]";
	}

}
