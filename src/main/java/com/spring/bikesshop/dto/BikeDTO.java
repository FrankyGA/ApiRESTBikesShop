package com.spring.bikesshop.dto;

public class BikeDTO {

	private Long id;
	private String name;
	private String brand;
	private String shop;
	
	public BikeDTO () {}

	public BikeDTO(String name, String brand, String shop) {
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

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}
}
