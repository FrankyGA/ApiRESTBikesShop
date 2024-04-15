package com.spring.bikesshop.controller;

import com.spring.bikesshop.converter.ShopConvertTo;
import com.spring.bikesshop.dto.ShopDTO;
import com.spring.bikesshop.exceptions.ResourceNotFoundException;
import com.spring.bikesshop.model.Bike;
import com.spring.bikesshop.model.Shop;
import com.spring.bikesshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ShopController {

	private final ShopRepository shopRepository;

	@Autowired
    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping("/shops")
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        List<ShopDTO> shopDTOs = shops.stream()
                .map(ShopConvertTo::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(shopDTOs);
    }

    @GetMapping("/shops/{id}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable Long id) {
        Optional<Shop> shopOptional = shopRepository.findById(id);
        Shop shop = shopOptional.orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + id));
        return ResponseEntity.ok(ShopConvertTo.convertToDTO(shop));
    }

    @PostMapping("/shops")
    public ResponseEntity<ShopDTO> createShop(@RequestBody ShopDTO shopDTO) {
        Shop shop = ShopConvertTo.convertToEntity(shopDTO);
        Shop savedShop = shopRepository.save(shop);
        return ResponseEntity.status(HttpStatus.CREATED).body(ShopConvertTo.convertToDTO(savedShop));
    }

    @PutMapping("/shops/{id}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Long id, @RequestBody ShopDTO updatedShopDTO) {
        Optional<Shop> shopOptional = shopRepository.findById(id);
        Shop shop = shopOptional.orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + id));
        shop.setName(updatedShopDTO.getName());
        shop.setAddress(updatedShopDTO.getAddress());
        Shop savedShop = shopRepository.save(shop);
        return ResponseEntity.ok(ShopConvertTo.convertToDTO(savedShop));
    }

    @DeleteMapping("/shops/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        if (shopRepository.existsById(id)) {
            shopRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException("Shop not found with id: " + id);
        }
    }	
}

/* ---------------------Sin DTO ni excepciones ---------------------
	 * // Contructor pasando el repositorio por parámetros para usar los métodos
	 * 
	 * @Autowired public ShopController(ShopRepository shopRepository) {
	 * this.shopRepository = shopRepository; }
	 * 
	 * // Petición GET para consultar todas los tiendas
	 * 
	 * @GetMapping("/shops") public ResponseEntity<List<Shop>> getAllShops() {
	 * List<Shop> shops = shopRepository.findAll(); return ResponseEntity.ok(shops);
	 * }
	 * 
	 * // Petición GET para consultar una tienda por su ID
	 * 
	 * @GetMapping("/shops/{id}") public ResponseEntity<Shop>
	 * getShopById(@PathVariable Long id) { Optional<Shop> shopOptional =
	 * shopRepository.findById(id); return
	 * shopOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()
	 * ); }
	 * 
	 * 
	 * // Petición POST para añadir una nueva tienda
	 * 
	 * @PostMapping("/shops") public ResponseEntity<Shop> createShop(@RequestBody
	 * Shop shop) { Shop savedShop = shopRepository.save(shop); return new
	 * ResponseEntity<>(savedShop, HttpStatus.CREATED); }
	 * 
	 * 
	 * // Petición POST para añadir una nueva tienda
	 * 
	 * @PostMapping("/shops") public ResponseEntity<Shop> createShop(@RequestBody
	 * Shop shop) { Shop savedShop = shopRepository.save(shop); // Guarda el nuevo
	 * cliente en la base de datos return
	 * ResponseEntity.status(HttpStatus.CREATED).body(savedShop);
	 * 
	 * }
	 * 
	 * // Petición PUT para modificar una tienda existente por su ID // Proviene del
	 * cuerpo de la solicitud http, hace la petición con los cambios // que desea
	 * hacer en la tienda especificada.
	 * 
	 * @PutMapping("/shops/{id}") public ResponseEntity<Shop>
	 * updateShop(@PathVariable Long id, @RequestBody Shop updatedShop) {
	 * Optional<Shop> shopOptional = shopRepository.findById(id); if
	 * (shopOptional.isPresent()) { updatedShop.setId(id); // Establece el ID en la
	 * tienda actualizada Shop savedShop = shopRepository.save(updatedShop); return
	 * ResponseEntity.ok(savedShop); } else { return
	 * ResponseEntity.notFound().build(); } }
	 * 
	 * // Petición DELETE para borrar una tienda por su ID
	 * 
	 * @DeleteMapping("/shops/{id}") public ResponseEntity<Void>
	 * deleteShop(@PathVariable Long id) { if (shopRepository.existsById(id)) {
	 * shopRepository.deleteById(id); return ResponseEntity.noContent().build(); }
	 * else { return ResponseEntity.notFound().build(); } }
	 */