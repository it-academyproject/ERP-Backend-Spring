package cat.itacademy.proyectoerp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.dto.ClientDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.service.IClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
	
	@Autowired
	IClientService service;
	
	
	 //Add a new Client
    @PostMapping
    public ResponseEntity<?> addNewClient(@RequestBody Client client) throws MethodArgumentTypeMismatchException{
//    	if(!client.getid().toString().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")){
//			throw new MethodArgumentTypeMismatchException("Invalid UUID", null, null, null, null);
//		}
    	try {
    		service.createClient(client);
    	} catch (ArgumentNotValidException e) {
    		return ResponseEntity.unprocessableEntity().body(e.getMessage());
    	}
        
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
    
    //Add client without giving user
    @PostMapping("/fastClient")
    public ResponseEntity<?> addFastClient(@RequestBody ClientDTO client) {
    	Client finalClient = service.createFastClient(client);
    	return ResponseEntity.ok().body(finalClient);
    }
    
    //Get all the clients
    @GetMapping
    public ResponseEntity<?> getAllClients() {
        List<Client> list = new ArrayList<>();
		try {
			list = service.getAllClients();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
		}
        return ResponseEntity.ok().body(list);
    }
    
    //Get clients per page
    @GetMapping("/list/{amount}/{page}")
    public ResponseEntity<HashMap<String, Object>> getClientsByPage(@PathVariable int amount, @PathVariable int page) {
    	List<ClientDTO> list;
    	HashMap<String, Object> map = new HashMap<>();
		try {
			list = service.getPageOfClients(page, amount);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
    	map.put("totalClients", service.getAllClients().size());
    	map.put("ClientsOfThePage", list);
    	return ResponseEntity.ok().body(map);
    	
    }

    //get a client by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable UUID id) {
        Optional<Client> client = null;
    	try {
			client = service.findClientById(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    	return ResponseEntity.ok().body(client);
    }

    //Update a client by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClientById(@RequestBody Client clientUpdate) {
    	Client client = null;
        try {
			client = service.updateClient(clientUpdate);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    //Delete a client
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClientById(@PathVariable UUID id){
    	  try {
			service.deleteClient(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok().build();

    }	
    
    //pruebaDTO
	@GetMapping("/lista")
	public ResponseEntity<?> listAllUsers() {
		List<ClientDTO> list = new ArrayList<>();
		try {
			list = service.listAllUsers();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	} 

}
