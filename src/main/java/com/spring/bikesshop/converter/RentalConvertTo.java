package com.spring.bikesshop.converter;

import com.spring.bikesshop.dto.RentalDTO;
import com.spring.bikesshop.model.Bike;
import com.spring.bikesshop.model.Client;
import com.spring.bikesshop.model.Rental;
import com.spring.bikesshop.model.Shop;

public class RentalConvertTo {

    public static RentalDTO convertToDTO(Rental rental) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setId(rental.getId());
        rentalDTO.setClient(rental.getClient().getName());
        rentalDTO.setBike(rental.getBike().getId());
        rentalDTO.setShop(rental.getShop().getName());
        rentalDTO.setStartDate(rental.getStartDate());
        rentalDTO.setEndDate(rental.getEndDate());
        rentalDTO.setPrice(rental.getPrice());
        return rentalDTO;
    }

    public static Rental convertToEntity(RentalDTO rentalDTO, Client client, Bike bike, Shop shop) {
        Rental rental = new Rental();
        rental.setClient(client);
        rental.setBike(bike);
        rental.setShop(shop);
        rental.setStartDate(rentalDTO.getStartDate());
        rental.setEndDate(rentalDTO.getEndDate());
        rental.setPrice(rentalDTO.getPrice());
        return rental;
    }
}

