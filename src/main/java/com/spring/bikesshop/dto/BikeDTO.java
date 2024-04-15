package com.spring.bikesshop.dto;

public class BikeDTO {

	private Long id;
	private String name;
	private String marca;
	private String shop;
	
	public BikeDTO () {}

	public BikeDTO(String name, String marca, String shop) {
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

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}
}
