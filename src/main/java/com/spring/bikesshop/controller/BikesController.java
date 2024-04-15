package com.spring.bikesshop.controller;

import com.spring.bikesshop.converter.BikeConvertTo;
import com.spring.bikesshop.converter.ClientConvertTo;
import com.spring.bikesshop.dto.BikeDTO;
import com.spring.bikesshop.exceptions.ResourceNotFoundException;
import com.spring.bikesshop.model.Bike;
import com.spring.bikesshop.model.Shop;
import com.spring.bikesshop.repository.BikeRepository;
import com.spring.bikesshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BikesController {

	private final BikeRepository bikeRepository;
	private final ShopRepository shopRepository;

	@Autowired
	public BikesController(BikeRepository bikeRepository, ShopRepository shopRepository) {
		this.bikeRepository = bikeRepository;
		this.shopRepository = shopRepository;
	}

	//-------------- M�todos peticiones Consultar --------------//
	
	@GetMapping("/bikes")
	public ResponseEntity<List<BikeDTO>> getAllBikes() {
		List<Bike> bikes = bikeRepository.findAll();
		List<BikeDTO> bikeDTOs = bikes.stream().map(this::convertToDTO).collect(Collectors.toList());
		return ResponseEntity.ok(bikeDTOs);
	}

	@GetMapping("/bikes/{id}")
	public ResponseEntity<BikeDTO> getBikeById(@PathVariable Long id) {
		Optional<Bike> bikeOptional = bikeRepository.findById(id);
		Bike bike = bikeOptional.orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + id));
		BikeDTO bikeDTO = convertToDTO(bike);
		return ResponseEntity.ok(bikeDTO);
	}
	
	//-------------- M�todos peticiones Insertar --------------//

	@PostMapping("/bikes")
	public ResponseEntity<BikeDTO> createBike(@RequestBody BikeDTO bikeDTO) {
		// Buscar la tienda por nombre
		Shop shop = shopRepository.findByName(bikeDTO.getShop())
				.orElseThrow(() -> new ResourceNotFoundException("Shop not found with name: " + bikeDTO.getShop()));
		// Crear una nueva bicicleta con los datos del post
		Bike bike = new Bike(bikeDTO.getName(), bikeDTO.getMarca(), shop);
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
	 * excepci�n ResourceNotFoundException. Utiliza la tienda encontrada (shop) para
	 * crear una nueva instancia de Bike con los detalles proporcionados en bikeDTO.
	 * Guarda la nueva bicicleta (bike) en la base de datos utilizando
	 * bikeRepository.save(bike). Convierte la bicicleta guardada (savedBike) en un
	 * DTO (savedBikeDTO) utilizando un m�todo de conversi�n, posiblemente definido
	 * en la clase BikeConvertTo Retorna un ResponseEntity con el c�digo de estado
	 * HttpStatus.CREATED y el cuerpo (body) que contiene el DTO de la bicicleta
	 * guardada.
	 */

	//-------------- M�todos peticiones Modificar --------------//
	
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
		bike.setMarca(updatedBikeDTO.getMarca());
		bike.setShop(shop);
		// Guardar la bicicleta actualizada en la base de datos
		Bike savedBike = bikeRepository.save(bike);
		// Convertir la bicicleta guardada a DTO y retornarla en la respuesta
		return ResponseEntity.ok(BikeConvertTo.convertToDTO(savedBike));
	}

	//-------------- M�todos peticiones Borrar --------------//
	
	@DeleteMapping("/bikes/{id}")
	public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
		if (bikeRepository.existsById(id)) {
			bikeRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			throw new ResourceNotFoundException("Bike not found with id: " + id);
		}
	}

	
	// M�todo para convertir una Bike a BikeDTO
	private BikeDTO convertToDTO(Bike bike) {
		BikeDTO bikeDTO = new BikeDTO();
		bikeDTO.setId(bike.getId());
		bikeDTO.setName(bike.getName());
		bikeDTO.setMarca(bike.getMarca());
		bikeDTO.setShop(bike.getShop().getName()); // Obtener el nombre de la tienda asociada
		return bikeDTO;
	}
}

/*
 * ---------------------Sin DTO ni excepciones --------------------- //
 * Contructor pasando el repositorio por par�metros para usar los m�todos
 * 
 * @Autowired public BikesController(BikesRepository bikesRepository) {
 * this.bikesRepository = bikesRepository; }
 * 
 * // Petici�n GET para consultar todas los bicis
 * 
 * @GetMapping("/bikes") public ResponseEntity<List<Bike>> getAllBikes() {
 * List<Bike> bikes = bikesRepository.findAll(); return
 * ResponseEntity.ok(bikes); }
 * 
 * // Petici�n GET para consultar una bici por su ID
 * 
 * @GetMapping("/bikes/{id}") public ResponseEntity<Bike>
 * getBikeById(@PathVariable Long id) { Optional<Bike> bikeOptional =
 * bikesRepository.findById(id); return
 * bikeOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()
 * ); }
 * 
 * 
 * // Petici�n POST para a�adir una nueva bici
 * 
 * @PostMapping("/bikes") public ResponseEntity<Bike> createBike(@RequestBody
 * Bike bike) { Bike savedBike = bikesRepository.save(bike); return new
 * ResponseEntity<>(savedBike, HttpStatus.CREATED); }
 * 
 * 
 * // Petici�n POST para a�adir una nueva bici
 * 
 * @PostMapping("/bikes") public ResponseEntity<Bike> createBike(@RequestBody
 * Bike bike) { Bike savedBike = bikesRepository.save(bike); // Guarda el nuevo
 * cliente en la base de datos return
 * ResponseEntity.status(HttpStatus.CREATED).body(savedBike);
 * 
 * }
 * 
 * // Petici�n PUT para modificar una bici existente por su ID // Proviene del
 * cuerpo de la solicitud http, hace la petici�n con los cambios // que desea
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
 * // Petici�n DELETE para borrar una bici por su ID
 * 
 * @DeleteMapping("/bikes/{id}") public ResponseEntity<Void>
 * deleteBike(@PathVariable Long id) { if (bikesRepository.existsById(id)) {
 * bikesRepository.deleteById(id); return ResponseEntity.noContent().build(); }
 * else { return ResponseEntity.notFound().build(); } }
 */