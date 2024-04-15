package com.spring.bikesshop.dto;

public class ShopDTO {

	private Long id;
	private String name;
	private String address;
	
	public ShopDTO () {}

	public ShopDTO(String name, String address) {
		this.name = name;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAdress(String address) {
		this.address = address;
	}
}
