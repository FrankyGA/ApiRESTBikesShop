package com.spring.bikesshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.bikesshop.model.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Optional<Client> findByName(String name);

}
