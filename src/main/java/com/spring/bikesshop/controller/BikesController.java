package com.spring.bikesshop.controller;

import com.spring.bikesshop.converter.BikeConvertTo;
import com.spring.bikesshop.dto.BikeDTO;
import com.spring.bikesshop.exceptions.ResourceNotFoundException;
import com.spring.bikesshop.model.Bike;
import com.spring.bikesshop.model.Shop;
import com.spring.bikesshop.repository.BikeRepository;
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
@Tag(name = "Bikes", description = "Bikes controller with CRUD Operations")
public class BikesController {

	private final BikeRepository bikeRepository;
	private final ShopRepository shopRepository;

	@Autowired
	public BikesController(BikeRepository bikeRepository, ShopRepository shopRepository) {
		this.bikeRepository = bikeRepository;
		this.shopRepository = shopRepository;
	}

	// -------------- Métodos peticiones Consultar --------------//
	
	// -------------- Petición todos las bicis --------------//
	
	@Operation(summary = "Get all bikes", description = "Get a list of all bikes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes found", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "No bikes found")
    })
	@GetMapping("/bikes")
	public ResponseEntity<List<BikeDTO>> getAllBikes() {
		List<Bike> bikes = bikeRepository.findAll();
		List<BikeDTO> bikeDTOs = bikes.stream().map(this::convertToDTO).collect(Collectors.toList());
		return ResponseEntity.ok(bikeDTOs);
	}
	
	// -------------- Petición bici por ID --------------//

	@Operation(summary = "Get bike by ID", description = "Get a bike by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike found", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "Bike not found")
    })
	@GetMapping("/bikes/{id}")
	public ResponseEntity<BikeDTO> getBikeById(@PathVariable Long id) {
		Optional<Bike> bikeOptional = bikeRepository.findById(id);
		Bike bike = bikeOptional.orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + id));
		BikeDTO bikeDTO = convertToDTO(bike);
		return ResponseEntity.ok(bikeDTO);
	}

	// -------------- Petición HEAD --------------//
	
	@Operation(summary = "Check if bike exists", description = "Check if a bike exists by ID without retrieving full response body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike found"),
            @ApiResponse(responseCode = "404", description = "Bike not found")
    })
	// Método para obtener metadatos de un recurso sin recuperar el cuerpo de la
	// respuesta completa
	@RequestMapping(value = "/bikes/{id}", method = RequestMethod.HEAD)
	public ResponseEntity<Void> getBikeMetadata(@PathVariable Long id) {
		if (bikeRepository.existsById(id)) {
			// Si la bici existe, retornar una respuesta exitosa sin contenido
			return ResponseEntity.ok().build();
		} else {
			// Si el cliente no existe, retornar una respuesta 404 (Not Found)
			return ResponseEntity.notFound().build();
		}
	}

	// -------------- Métodos peticiones Insertar --------------//
	
	@Operation(summary = "Create a new bike", description = "Create a new bike")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bike created", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
	@PostMapping("/bikes")
	public ResponseEntity<BikeDTO> createBike(@RequestBody BikeDTO bikeDTO) {
		// Buscar la tienda por nombre
		Shop shop = shopRepository.findByName(bikeDTO.getShop())
				.orElseThrow(() -> new ResourceNotFoundException("Shop not found with name: " + bikeDTO.getShop()));
		// Crear una nueva bicicleta con los datos del post
		Bike bike = new Bike(bikeDTO.getName(), bikeDTO.getBrand(), shop);
		// Guardar la bicicleta en la base de datos
		Bike savedBike = bikeRepository.save(bike);
		// Convertir la bicicleta guardada a DTO
		BikeDTO savedBikeDTO = convertToDTO(savedBike);
		// Retornar el DTO de la bicicleta guardada en la respuesta
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBikeDTO);
	}

	/*
	 * Utiliza shopRepository para buscar una tienda por el nombre proporcionado en
	 * bikeDTO.getShop(). Si la tienda no se encuentra (orElseThrow()), se lanza una
	 * excepción ResourceNotFoundException. Utiliza la tienda encontrada (shop) para
	 * crear una nueva instancia de Bike con los detalles proporcionados en bikeDTO.
	 * Guarda la nueva bicicleta (bike) en la base de datos utilizando
	 * bikeRepository.save(bike). Convierte la bicicleta guardada (savedBike) en un
	 * DTO (savedBikeDTO) utilizando un método de conversión, posiblemente definido
	 * en la clase BikeConvertTo Retorna un ResponseEntity con el código de estado
	 * HttpStatus.CREATED y el cuerpo (body) que contiene el DTO de la bicicleta
	 * guardada.
	 */

	// -------------- Métodos peticiones Modificar --------------//
	
	// -------------- Petición modificación total bici --------------//

	@Operation(summary = "Update bike by ID", description = "Update an existing bike by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike updated", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "Bike not found or shop not found")
    })
	@PutMapping("/bikes/{id}")
	public ResponseEntity<BikeDTO> updateBike(@PathVariable Long id, @RequestBody BikeDTO updatedBikeDTO) {
		// Buscar la bicicleta por ID
		Optional<Bike> bikeOptional = bikeRepository.findById(id);
		Bike bike = bikeOptional.orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + id));
		// Buscar la tienda por nombre (de la bicicleta actualizada)
		Shop shop = shopRepository.findByName(updatedBikeDTO.getShop()).orElseThrow(
				() -> new ResourceNotFoundException("Shop not found with name: " + updatedBikeDTO.getShop()));
		// Si se ha encontrado la bici
		// Actualizar los atributos de la bicicleta con los datos actualizados
		bike.setName(updatedBikeDTO.getName());
		bike.setBrand(updatedBikeDTO.getBrand());
		bike.setShop(shop);
		// Guardar la bicicleta actualizada en la base de datos
		Bike savedBike = bikeRepository.save(bike);
		// Convertir la bicicleta guardada a DTO y retornarla en la respuesta
		return ResponseEntity.ok(BikeConvertTo.convertToDTO(savedBike));
	}
	
	// -------------- Petición modificación de atributo bici --------------//

	@Operation(summary = "Update bike name by ID", description = "Update the name of an existing bike by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike name updated", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "Bike not found")
    })
	// Método para actualizar solo el nombre de una bici
	@PatchMapping("/bikes/{id}/updateName")
	public ResponseEntity<BikeDTO> updateBikeName(@PathVariable Long id, @RequestBody String newName) {
		Optional<Bike> bikeOptional = bikeRepository.findById(id);
		Bike bike = bikeOptional.orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + id));
		// Actualizar solo el nombre de la bici
		bike.setName(newName);
		// Guardar la bici actualizado en la base de datos
		Bike savedBike = bikeRepository.save(bike);
		// Retornar la bici actualizada en formato DTO
		return ResponseEntity.ok(BikeConvertTo.convertToDTO(savedBike));
	}

	// -------------- Métodos peticiones Borrar --------------//

	@Operation(summary = "Delete bike by ID", description = "Delete a bike by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bike deleted"),
            @ApiResponse(responseCode = "404", description = "Bike not found")
    })
	@DeleteMapping("/bikes/{id}")
	public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
		if (bikeRepository.existsById(id)) {
			bikeRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			throw new ResourceNotFoundException("Bike not found with id: " + id);
		}
	}

	// Método para convertir una Bike a BikeDTO
	private BikeDTO convertToDTO(Bike bike) {
		BikeDTO bikeDTO = new BikeDTO();
		bikeDTO.setId(bike.getId());
		bikeDTO.setName(bike.getName());
		bikeDTO.setBrand(bike.getBrand());
		bikeDTO.setShop(bike.getShop().getName()); // Obtener el nombre de la tienda asociada
		return bikeDTO;
	}
}

/*
 * ---------------------Sin DTO ni excepciones --------------------- //
 * Contructor pasando el repositorio por parámetros para usar los métodos
 * 
 * @Autowired public BikesController(BikesRepository bikesRepository) {
 * this.bikesRepository = bikesRepository; }
 * 
 * // Petición GET para consultar todas los bicis
 * 
 * @GetMapping("/bikes") public ResponseEntity<List<Bike>> getAllBikes() {
 * List<Bike> bikes = bikesRepository.findAll(); return
 * ResponseEntity.ok(bikes); }
 * 
 * // Petición GET para consultar una bici por su ID
 * 
 * @GetMapping("/bikes/{id}") public ResponseEntity<Bike>
 * getBikeById(@PathVariable Long id) { Optional<Bike> bikeOptional =
 * bikesRepository.findById(id); return
 * bikeOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()
 * ); }
 * 
 * 
 * // Petición POST para añadir una nueva bici
 * 
 * @PostMapping("/bikes") public ResponseEntity<Bike> createBike(@RequestBody
 * Bike bike) { Bike savedBike = bikesRepository.save(bike); return new
 * ResponseEntity<>(savedBike, HttpStatus.CREATED); }
 * 
 * 
 * // Petición POST para añadir una nueva bici
 * 
 * @PostMapping("/bikes") public ResponseEntity<Bike> createBike(@RequestBody
 * Bike bike) { Bike savedBike = bikesRepository.save(bike); // Guarda el nuevo
 * cliente en la base de datos return
 * ResponseEntity.status(HttpStatus.CREATED).body(savedBike);
 * 
 * }
 * 
 * // Petición PUT para modificar una bici existente por su ID // Proviene del
 * cuerpo de la solicitud http, hace la petición con los cambios // que desea
 * hacer en la bici especificada.
 * 
 * @PutMapping("/bikes/{id}") public ResponseEntity<Bike>
 * updateBike(@PathVariable Long id, @RequestBody Bike updatedBike) {
 * Optional<Bike> bikeOptional = bikesRepository.findById(id); if
 * (bikeOptional.isPresent()) { updatedBike.setId(id); // Establece el ID en la
 * bicicleta actualizada Bike savedBike = bikesRepository.save(updatedBike);
 * return ResponseEntity.ok(savedBike); } else { return
 * ResponseEntity.notFound().build(); } }
 * 
 * // Petición DELETE para borrar una bici por su ID
 * 
 * @DeleteMapping("/bikes/{id}") public ResponseEntity<Void>
 * deleteBike(@PathVariable Long id) { if (bikesRepository.existsById(id)) {
 * bikesRepository.deleteById(id); return ResponseEntity.noContent().build(); }
 * else { return ResponseEntity.notFound().build(); } }
 */