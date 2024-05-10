package com.spring.bikesshop.model;

import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Rental bikes", description = "Rental bikes for clients")
@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "ID for rental")
    private Long idRental;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "idBike")
    private Bike bike;

    @ManyToOne
    @JoinColumn(name = "idShop")
    private Shop shop;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyy")
    @Schema(example = "01-04-2024", description = "Start date of the rental (dd-mm-yyyy)")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyy")
    @Schema(example = "15-04-2024", description = "End date of the rental (dd-mm-yyyy)")
    private Date endDate;

    @Schema(example = "50.0", description = "Price of the rental")
    private Double price;

	/*
	 * public Rental(Long id, Client client, Bike bike, Shop shop, @NotNull Date
	 * startDate, @NotNull Date endDate, Double price) { this.idRental = id;
	 * this.client = client; this.bike = bike; this.shop = shop; this.startDate =
	 * startDate; this.endDate = endDate; this.price = price; }
	 * 
	 * public Rental() {}
	 * 
	 * public Long getId() { return idRental; }
	 * 
	 * public void setId(Long idRental) { this.idRental = idRental; }
	 * 
	 * public Client getClient() { return client; }
	 * 
	 * public void setClient(Client client) { this.client = client; }
	 * 
	 * public Bike getBike() { return bike; }
	 * 
	 * public void setBike(Bike bike) { this.bike = bike; }
	 * 
	 * public Shop getShop() { return shop; }
	 * 
	 * public void setShop(Shop shop) { this.shop = shop; }
	 * 
	 * public Date getStartDate() { return startDate; }
	 * 
	 * public void setStartDate(Date startDate) { this.startDate = startDate; }
	 * 
	 * public Date getEndDate() { return endDate; }
	 * 
	 * public void setEndDate(Date endDate) { this.endDate = endDate; }
	 * 
	 * public Double getPrice() { return price; }
	 * 
	 * public void setPrice(Double price) { this.price = price; }
	 * 
	 * @Override public int hashCode() { return Objects.hash(bike, client, endDate,
	 * idRental, price, shop, startDate); }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; Rental other = (Rental) obj; return Objects.equals(bike, other.bike)
	 * && Objects.equals(client, other.client) && Objects.equals(endDate,
	 * other.endDate) && Objects.equals(idRental, other.idRental) &&
	 * Objects.equals(price, other.price) && Objects.equals(shop, other.shop) &&
	 * Objects.equals(startDate, other.startDate); }
	 * 
	 * @Override public String toString() { return "Rental [id=" + idRental +
	 * ", client=" + client + ", bike=" + bike + ", shop=" + shop + ", startDate=" +
	 * startDate + ", endDate=" + endDate + ", price=" + price + "]"; }
	 */
}

