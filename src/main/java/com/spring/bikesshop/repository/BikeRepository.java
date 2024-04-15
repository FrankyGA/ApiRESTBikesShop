package com.spring.bikesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.bikesshop.model.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long> {

}
