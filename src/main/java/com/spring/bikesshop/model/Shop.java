package com.spring.bikesshop.model;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity @Data
public class Shop {
	
	@Id
	@GeneratedValue
	@Schema(example = "1", description = "ID for shop")
	private Long id;
	@NotNull(message = "Name is required")
    @Size(min = 3, message = "Title must be at least 3 characters long")
	@Schema(example = "Carrefour", description = "Name for shop")
	private String name;
	@NotNull(message = "Address is required")
	@Schema(example = "Street....", description = "Street for shop")
	private String address;
	
	public Shop () {}

	public Shop(String name, String address) {
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

	@Override
	public int hashCode() {
		return Objects.hash(address, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		return Objects.equals(address, other.address) && Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Shop [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
}
