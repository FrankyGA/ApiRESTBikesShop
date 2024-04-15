package com.spring.bikesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.bikesshop.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
