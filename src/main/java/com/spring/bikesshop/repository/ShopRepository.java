package com.spring.bikesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.bikesshop.model.Shop;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long>{
	
	Optional<Shop> findByName(String name);

}
