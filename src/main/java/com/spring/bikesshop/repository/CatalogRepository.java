package com.spring.bikesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.bikesshop.model.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    
}

