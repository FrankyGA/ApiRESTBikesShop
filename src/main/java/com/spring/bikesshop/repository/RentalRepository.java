package com.spring.bikesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.bikesshop.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {

}
