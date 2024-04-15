package com.spring.bikesshop.controller;

import com.spring.bikesshop.converter.ClientConvertTo;
import com.spring.bikesshop.dto.ClientDTO;
import com.spring.bikesshop.exceptions.ResourceNotFoundException;
import com.spring.bikesshop.model.Client;
import com.spring.bikesshop.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

	private final ClientRepository clientRepository;

	@Autowired
	public ClientController(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	// -------------- Métodos peticiones Consultar --------------//

	@GetMapping("/clients")
	public ResponseEntity<List<ClientDTO>> getAllClients() {
		List<Client> clients = clientRepository.findAll();
		return ResponseEntity.ok(ClientConvertTo.convertToDTOList(clients));
	}

	@GetMapping("/clients/{id}")
	public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
		Optional<Client> clientOptional = clientRepository.findById(id);
		Client client = clientOptional
				.orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
		return ResponseEntity.ok(ClientConvertTo.convertToDTO(client));
	}

	// Método para obtener metadatos de un recurso sin recuperar el cuerpo de la respuesta completa
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.HEAD)
	public ResponseEntity<Void> getClientMetadata(@PathVariable Long id) {
		if (clientRepository.existsById(id)) {
			// Si el cliente existe, retornar una respuesta exitosa sin contenido
			return ResponseEntity.ok().build();
		} else {
			// Si el cliente no existe, retornar una respuesta 404 (Not Found)
			return ResponseEntity.notFound().build();
		}
	}

	// -------------- Métodos peticiones Insertar --------------//

	@PostMapping("/clients")
	public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
		Client client = ClientConvertTo.convertToEntity(clientDTO);
		Client savedClient = clientRepository.save(client);
		return ResponseEntity.status(HttpStatus.CREATED).body(ClientConvertTo.convertToDTO(savedClient));
	}

	// -------------- Métodos peticiones Modificar --------------//

	// Petición para modificar cliente
	@PutMapping("/clients/{id}")
	public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO updatedClientDTO) {
		// Busca cliente por id para guardarlo en un objeto cliente
		Optional<Client> clientOptional = clientRepository.findById(id);
		Client client = clientOptional
				.orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
		// Guarda los atributos que se pasan en el cuerpo de la petición
		client.setName(updatedClientDTO.getName());
		client.setAddress(updatedClientDTO.getAddress());
		client.setAge(updatedClientDTO.getAge());
		// Guarda el cliente modificado y y convierte en DTO para enviar los datos a la
		// respuesta
		Client savedClient = clientRepository.save(client);
		return ResponseEntity.ok(ClientConvertTo.convertToDTO(savedClient));
	}

	// Método para actualizar solo el nombre de un cliente
	@PatchMapping("/clients/{id}/updateName")
	public ResponseEntity<ClientDTO> updateClientName(@PathVariable Long id, @RequestBody String newName) {
		Optional<Client> clientOptional = clientRepository.findById(id);
		Client client = clientOptional
				.orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
		// Actualizar solo el nombre del cliente
		client.setName(newName);
		// Guardar el cliente actualizado en la base de datos
		Client savedClient = clientRepository.save(client);
		// Retornar el cliente actualizado en formato DTO
		return ResponseEntity.ok(ClientConvertTo.convertToDTO(savedClient));
	}

	// -------------- Métodos peticiones Borrar --------------//

	@DeleteMapping("/clients/{id}")
	public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
		if (clientRepository.existsById(id)) {
			clientRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			throw new ResourceNotFoundException("Client not found with id: " + id);
		}
	}
}

/*
 * ---------------------Sin DTO ni excepciones --------------------- //
 * Constructor que inyecta ClientRepository mediante autowiring
 * 
 * @Autowired public ClientController(ClientRepository clientRepository) {
 * this.clientRepository = clientRepository; }
 * 
 * // Petición GET para consultar todos los clientes
 * 
 * @GetMapping("/clients") public ResponseEntity<List<Client>> getAllClients() {
 * List<Client> clients = clientRepository.findAll(); return
 * ResponseEntity.ok(clients); // Devuelve lista de clientes y estado 200 (OK) }
 * 
 * // Petición GET para consultar un cliente por su ID
 * 
 * @GetMapping("/clients/{id}") public ResponseEntity<Client>
 * getClientById(@PathVariable Long id) { Optional<Client> clientOptional =
 * clientRepository.findById(id); // Optional para evitar la devolución de null,
 * // y devolver un objeto optional vacio // Verifica si el cliente con el ID
 * dado existe en la base de datos return clientOptional.map(client ->
 * ResponseEntity.ok(client)) // Devuelve cliente y estado 200 (OK) si existe
 * .orElse(ResponseEntity.notFound().build()); // Devuelve estado 404 (Not
 * Found) si no existe }
 * 
 * 
 * // Petición POST para añadir un nuevo cliente
 * 
 * @PostMapping public ResponseEntity<Client> createClient(@RequestBody Client
 * client) { Client savedClient = clientRepository.save(client); // Guarda el
 * nuevo cliente en la base de datos return new ResponseEntity<>(savedClient,
 * HttpStatus.CREATED); // Devuelve el cliente guardado y estado 201 //
 * (Created) }
 * 
 * 
 * // Petición POST para añadir un nuevo cliente
 * 
 * @PostMapping("/clients") public ResponseEntity<Client>
 * createClient(@RequestBody Client client) { Client savedClient =
 * clientRepository.save(client); // Guarda el nuevo cliente en la base de datos
 * return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
 * 
 * }
 * 
 * // Petición PUT para modificar un cliente existente por su ID // Proviene del
 * cuerpo de la solicitud http, hace la petición con los cambios que desea hacer
 * en el cliente especificado.
 * 
 * @PutMapping("/clients/{id}") public ResponseEntity<Client>
 * updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
 * Optional<Client> clientOptional = clientRepository.findById(id); if
 * (clientOptional.isPresent()) { // Verifica si el cliente con el ID dado
 * existe en la base de datos updatedClient.setId(id); // Establece el ID en el
 * cliente actualizado Client savedClient =
 * clientRepository.save(updatedClient); // Guarda la actualización del cliente
 * en la base // de datos return ResponseEntity.ok(savedClient); // Devuelve el
 * cliente actualizado y estado 200 (OK) } else { return
 * ResponseEntity.notFound().build(); // Devuelve estado 404 (Not Found) si el
 * cliente no existe } }
 * 
 * // Petición DELETE para borrar un cliente por su ID
 * 
 * @DeleteMapping("/clients/{id}") public ResponseEntity<Void>
 * deleteClient(@PathVariable Long id) { if (clientRepository.existsById(id)) {
 * // Verifica si el cliente con el ID dado existe en la base de datos
 * clientRepository.deleteById(id); // Borra el cliente de la base de datos
 * return ResponseEntity.noContent().build(); // Devuelve estado 204 (No
 * Content) después de borrar } else { return ResponseEntity.notFound().build();
 * // Devuelve estado 404 (Not Found) si el cliente no existe } }
 */
