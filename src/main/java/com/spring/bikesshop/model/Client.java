package com.spring.bikesshop.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Clients", description = "Clients")
@Entity
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(example = "1", description = "ID for client")
	private Long idClient;
	@NotNull(message = "Name is required")
    @Size(min = 3, message = "Title must be at least 3 characters long")
	@Schema(example = "Alan", description = "Name for client")
	private String name;
	@NotNull(message = "Address is required")
	@Schema(example = "Street river west, 4", description = "Address for client")
	private String address;
	@Schema(example = "25", description = "Age for client")
	private String age;

	/*
	 * public Client() {}
	 * 
	 * public Client(String name, String address, String age) { this.name = name;
	 * this.address = address; this.age = age; }
	 * 
	 * public Long getId() { return idClient; }
	 * 
	 * public void setId(Long id) { this.idClient = id; }
	 * 
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 * 
	 * public String getAddress() { return address; }
	 * 
	 * public void setAddress(String address) { this.address = address;
	 * 
	 * }
	 * 
	 * public String getAge() { return age; }
	 * 
	 * public void setAge(String age) { this.age = age; }
	 * 
	 * @Override public int hashCode() { return Objects.hash(address, age, idClient,
	 * name); }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; Client other = (Client) obj; return Objects.equals(address,
	 * other.address) && Objects.equals(age, other.age) && Objects.equals(idClient,
	 * other.idClient) && Objects.equals(name, other.name); }
	 * 
	 * @Override public String toString() { return "Client [id=" + idClient +
	 * ", name=" + name + ", address=" + address + ", age=" + age + "]"; }
	 */

	
}
