package com.spring.bikesshop.controller;

import com.spring.bikesshop.converter.BikeConvertTo;
import com.spring.bikesshop.converter.ShopConvertTo;
import com.spring.bikesshop.dto.BikeDTO;
import com.spring.bikesshop.dto.ShopDTO;
import com.spring.bikesshop.exceptions.ResourceNotFoundException;
import com.spring.bikesshop.exceptions.ValidationException;
import com.spring.bikesshop.model.Bike;
import com.spring.bikesshop.model.Client;
import com.spring.bikesshop.model.Shop;
import com.spring.bikesshop.repository.ShopRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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

	// -------------- Métodos peticiones Consultar --------------//
	
	// -------------- Petición todos las tiendas --------------//

	@Operation(summary = "Get all shops", description = "Get a list of all shops")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops found, retrieved shops", content = {
            		@Content(mediaType = "application/json",
            				array = @ArraySchema(schema = @Schema(implementation = Shop.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Shops not found")
    })
	@GetMapping("/shops")
	public ResponseEntity<List<ShopDTO>> getAllShops() {
		List<Shop> shops = shopRepository.findAll();
		if (shops.isEmpty()) {
            throw new ResourceNotFoundException("No shops found");
        }
		List<ShopDTO> shopDTOs = shops.stream().map(ShopConvertTo::convertToDTO).collect(Collectors.toList());
		return ResponseEntity.ok(shopDTOs);
	}
	
	// -------------- Petición tienda por ID --------------//

	@Operation(summary = "Get shop by ID", description = "Get a shop by its ID")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop found", content = {
            		@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
	@GetMapping("/shops/{id}")
	public ResponseEntity<ShopDTO> getShopById(@PathVariable Long id) {
		Optional<Shop> shopOptional = shopRepository.findById(id);
		Shop shop = shopOptional.orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + id));
		return ResponseEntity.ok(ShopConvertTo.convertToDTO(shop));
	}
	
	// -------------- Petición HEAD --------------//

	// Método para obtener metadatos de un recurso sin recuperar el cuerpo de la
	// respuesta completa
	@Operation(summary = "Check if shop exists", description = "Check if a shop exists by ID without retrieving full response body")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop found"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
	@RequestMapping(value = "/shops/{id}", method = RequestMethod.HEAD)
	public ResponseEntity<Void> getShopMetadata(@PathVariable Long id) {
		if (shopRepository.existsById(id)) {
			// Si la tienda existe, retornar una respuesta exitosa sin contenido
			return ResponseEntity.ok().build();
		} else {
			// Si el cliente no existe, retornar una respuesta 404 (Not Found)
			return ResponseEntity.notFound().build();
		}
	}

	// -------------- Métodos peticiones Insertar --------------//

	@Operation(summary = "Create a new shop", description = "Create a new shop")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Shop created"),
            @ApiResponse(responseCode = "400", description = "Invalid shop data provided")
    })
	@PostMapping("/shops")
	public ResponseEntity<ShopDTO> createShop(@RequestBody ShopDTO shopDTO) {
		if (shopDTO == null) {
            throw new ValidationException("Invalid shop data provided");
        }
		Shop shop = ShopConvertTo.convertToEntity(shopDTO);
		Shop savedShop = shopRepository.save(shop);
		return ResponseEntity.status(HttpStatus.CREATED).body(ShopConvertTo.convertToDTO(savedShop));
	}

	// -------------- Métodos peticiones Modificar --------------//
	
	// -------------- Petición modificación total tienda --------------//

	@Operation(summary = "Update shop by ID", description = "Update an existing shop by its ID")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop updated"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
	@PutMapping("/shops/{id}")
	public ResponseEntity<ShopDTO> updateShop(@PathVariable Long id, @RequestBody ShopDTO updatedShopDTO) {
		Optional<Shop> shopOptional = shopRepository.findById(id);
		Shop shop = shopOptional.orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + id));
		shop.setName(updatedShopDTO.getName());
		shop.setAddress(updatedShopDTO.getAddress());
		Shop savedShop = shopRepository.save(shop);
		return ResponseEntity.ok(ShopConvertTo.convertToDTO(savedShop));
	}
	
	// -------------- Petición modificación de atributo tienda --------------//
	
	@Operation(summary = "Update shop name by ID", description = "Update the name of an existing shop by its ID")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop name updated"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
	// Método para actualizar solo el nombre de una tienda
		@PatchMapping("/shops/{id}/updateName")
		public ResponseEntity<ShopDTO> updateShopName(@PathVariable Long id, @RequestBody String newName) {
			Optional<Shop> shopOptional = shopRepository.findById(id);
			Shop shop = shopOptional.orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + id));
			// Actualizar solo el nombre de la tienda
			shop.setName(newName);
			// Guardar la tienda actualizado en la base de datos
			Shop savedShop = shopRepository.save(shop);
			// Retornar la tienda actualizada en formato DTO
			return ResponseEntity.ok(ShopConvertTo.convertToDTO(savedShop));
		}

	// -------------- Métodos peticiones Borrar --------------//

	@Operation(summary = "Delete shop by ID", description = "Delete a shop by its ID")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Shop deleted"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
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

/*
 * ---------------------Sin DTO ni excepciones --------------------- //
 * Contructor pasando el repositorio por parámetros para usar los métodos
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