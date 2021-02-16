package cat.itacademy.proyectoerp.controller;

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

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.service.IClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
	
	@Autowired
	IClientService service;
	
	
	 //Add a new Client
    @PostMapping
    public ResponseEntity<?> addNewClient(@RequestBody Client client) {
        service.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
    
    //Get all the clients
    @GetMapping
    public ResponseEntity<?> getAllClients() {
        List<Client> list = (List<Client>) service.getAllClients();
        return ResponseEntity.ok().body(list);
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

}
