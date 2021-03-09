package cat.itacademy.proyectoerp.controller;

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
        service.createClient(client);
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
        List<Client> list = (List<Client>) service.getAllClients();
        return ResponseEntity.ok().body(list);
    }
    
    //Get clients per page
    @GetMapping("/list/{amount}/{page}")
    public ResponseEntity<HashMap<String, Object>> getClientsByPage(@PathVariable int amount, @PathVariable int page) {
    	List<ClientDTO> list = service.getPageOfClients(page, amount);
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("totalClients", service.getAllClients().size());
    	map.put("Clients of the page", list);
    	return ResponseEntity.ok().body(map);
    	
    }

    //get a client by id
    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable UUID id) {
        return service.findClientById(id);
    }

    //Update a client by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClientById(@RequestBody Client clientUpdate) {
        service.updateClient(clientUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientUpdate);
    }

    //Delete a client
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClientById(@PathVariable UUID id){
    	  service.deleteClient(id);
        return ResponseEntity.ok().build();

    }	
    
    //pruebaDTO
	@GetMapping("/lista")
	public ResponseEntity<List<ClientDTO>> listAllUsers() {
		return new ResponseEntity<>(service.listAllUsers(), HttpStatus.OK);
	} 

}
