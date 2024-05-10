package com.spring.bikesshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.bikesshop.converter.RentalConvertTo;
import com.spring.bikesshop.dto.CatalogDTO;
import com.spring.bikesshop.dto.RentalDTO;
import com.spring.bikesshop.exceptions.ResourceNotFoundException;
import com.spring.bikesshop.exceptions.ValidationException;
import com.spring.bikesshop.model.Bike;
import com.spring.bikesshop.model.Client;
import com.spring.bikesshop.model.Rental;
import com.spring.bikesshop.model.Shop;
import com.spring.bikesshop.repository.BikeRepository;
import com.spring.bikesshop.repository.ClientRepository;
import com.spring.bikesshop.repository.RentalRepository;
import com.spring.bikesshop.repository.ShopRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Rentals", description = "Rentals controller with CRUD Operations")
public class RentalController {

	private final RentalRepository rentalRepository;
	private final ClientRepository clientRepository;
	private final BikeRepository bikeRepository;
	private final ShopRepository shopRepository;

	@Autowired
	public RentalController(RentalRepository rentalRepository, ClientRepository clientRepository,
			BikeRepository bikeRepository, ShopRepository shopRepository) {
		this.rentalRepository = rentalRepository;
		this.clientRepository = clientRepository;
		this.bikeRepository = bikeRepository;
		this.shopRepository = shopRepository;
	}

	// -------------- Métodos peticiones Consultar --------------//

	// -------------- Petición todos los alquileres --------------//
	@Operation(summary = "Get all rentals", description = "Get a list of all rentals")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Rentals found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))),
			@ApiResponse(responseCode = "404", description = "No rentals found") })
	@GetMapping("/rentals")
	public ResponseEntity<List<RentalDTO>> getAllRentals() {
		List<Rental> rentals = rentalRepository.findAll();
		if (rentals.isEmpty()) {
			throw new ResourceNotFoundException("No rentals found");
		}
		return ResponseEntity.ok(rentals.stream().map(RentalConvertTo::convertToDTO).collect(Collectors.toList()));
	}

	// -------------- Petición alquiler por ID --------------//

	@Operation(summary = "Get rental fo ID", description = "Get a rental for ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Rental found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))),
			@ApiResponse(responseCode = "404", description = "No rentals found") })
	@GetMapping("/rentals/{id}")
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
		Optional<Rental> rentalOptional = rentalRepository.findById(id);
		Rental rental = rentalOptional
				.orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));
		return ResponseEntity.ok(RentalConvertTo.convertToDTO(rental));
	}

	// -------------- Métodos peticiones Insertar POST--------------//

	@Operation(summary = "Create a new rental", description = "Create a new rental")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Rental created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class)) }),
			@ApiResponse(responseCode = "404", description = "Not found") })
	@PostMapping("/rentals")
	public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
		if (rentalDTO == null || rentalDTO.getStartDate() == null || rentalDTO.getEndDate() == null) {
			throw new ValidationException("Invalid rental data provided");
		}

		// Busca el cliente por nombre
		Client client = clientRepository.findByName(rentalDTO.getClient()).orElseThrow(
				() -> new ResourceNotFoundException("Client not found with name: " + rentalDTO.getClient()));

		// Busca la bicicleta por ID (como está actualmente en el DTO)
		Bike bike = bikeRepository.findById(rentalDTO.getBike())
				.orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + rentalDTO.getBike()));

		// Busca la tienda por nombre
		Shop shop = shopRepository.findByName(rentalDTO.getShop())
				.orElseThrow(() -> new ResourceNotFoundException("Shop not found with name: " + rentalDTO.getShop()));

		Rental rental = RentalConvertTo.convertToEntity(rentalDTO, client, bike, shop);

		Rental savedRental = rentalRepository.save(rental);
		return ResponseEntity.status(HttpStatus.CREATED).body(RentalConvertTo.convertToDTO(savedRental));
	}

	// -------------- Petición modificación alquiler --------------//

	@Operation(summary = "Update an existing rental", description = "Update an existing rental")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rental updated", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class)) }),
			@ApiResponse(responseCode = "404", description = "Rental not found") })
	@PutMapping("/rentals/{id}")
	public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id,
			@Valid @RequestBody RentalDTO updatedRentalDTO) {
		Optional<Rental> rentalOptional = rentalRepository.findById(id);
		Rental rental = rentalOptional
				.orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));

		// Actualiza los campos modificables del alquiler solo si se proporcionan en el
		// DTO actualizado
		if (updatedRentalDTO.getStartDate() != null) {
			rental.setStartDate(updatedRentalDTO.getStartDate());
		}
		if (updatedRentalDTO.getEndDate() != null) {
			rental.setEndDate(updatedRentalDTO.getEndDate());
		}
		if (updatedRentalDTO.getPrice() != null) {
			rental.setPrice(updatedRentalDTO.getPrice());
		}

		// Si se proporciona el nombre del cliente en el DTO actualizado, busca el
		// cliente por nombre
		if (updatedRentalDTO.getClient() != null) {
			Client client = clientRepository.findByName(updatedRentalDTO.getClient()).orElseThrow(
					() -> new ResourceNotFoundException("Client not found with name: " + updatedRentalDTO.getClient()));
			rental.setClient(client);
		}

		// Si se proporciona el nombre de la tienda en el DTO actualizado, busca la
		// tienda por nombre
		if (updatedRentalDTO.getShop() != null) {
			Shop shop = shopRepository.findByName(updatedRentalDTO.getShop()).orElseThrow(
					() -> new ResourceNotFoundException("Shop not found with name: " + updatedRentalDTO.getShop()));
			rental.setShop(shop);
		}

		// Si se proporciona el ID de la bicicleta en el DTO actualizado, busca la
		// bicicleta por ID
		if (updatedRentalDTO.getBike() != null) {
			Bike bike = bikeRepository.findById(updatedRentalDTO.getBike()).orElseThrow(
					() -> new ResourceNotFoundException("Bike not found with id: " + updatedRentalDTO.getBike()));
			rental.setBike(bike);
		}

		// Guarda el alquiler actualizado en la base de datos
		Rental updatedRental = rentalRepository.save(rental);
		return ResponseEntity.ok(RentalConvertTo.convertToDTO(updatedRental));
	}

	// -------------- Métodos peticiones Borrar --------------//
	
	@Operation(summary = "Delete an existing rental by ID", description = "Delete an existing rental by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Rental deleted", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class)) }),
			@ApiResponse(responseCode = "404", description = "Rental not found") })
	@DeleteMapping("/rentals/{id}")
	public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
		if (rentalRepository.existsById(id)) {
			rentalRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			throw new ResourceNotFoundException("Rental not found with id: " + id);
		}
	}

}
