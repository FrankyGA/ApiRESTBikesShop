package com.spring.bikesshop.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class RentalDTO {

    private Long id;
    private String client;
    private Long bike;
    private String shop;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;
    private Double price;

    public RentalDTO(Long id, String client, Long bike, String shop, Date startDate, Date endDate, Double price) {
        this.id = id;
        this.client = client;
        this.bike = bike;
        this.shop = shop;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
    
    public RentalDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getBike() {
        return bike;
    }

    public void setBike(Long bike) {
        this.bike = bike;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
}

