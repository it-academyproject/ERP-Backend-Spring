package cat.itacademy.proyectoerp.controller;

import java.util.ArrayList;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.service.IUserService;
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

	@Autowired
	IUserService userService;
	
	
	 //Add a new Client
    @PostMapping
    public ResponseEntity<?> addNewClient(@Valid @RequestBody Client client) throws MethodArgumentTypeMismatchException{
		ClientDTO clientDTO = new ClientDTO();
		MessageDTO messageDTO;
		UserDTO userDTO = new UserDTO();

		if (service.existsByDni(client.getDni())) {
			messageDTO = new MessageDTO("False", "Dni already exists");
			return new ResponseEntity<>(messageDTO, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		try {
			userDTO = userService.getByUsername(client.getUser().getUsername()).get();
			if(userDTO.getSuccess().equalsIgnoreCase("False")){
				userDTO = userService.registerNewUserAccount(client.getUser());
			}
			User userRegistered = userService.findByUsername(userDTO.getUsername());

			Client newClient = new Client(client.getDni(),client.getImage(),client.getNameAndSurname(),
					client.getAddress(),client.getShippingAddress(),userRegistered);
			clientDTO = service.createClient(newClient);
    	} catch (Exception e) {
			messageDTO = new MessageDTO("False", e.getMessage().concat("User already assigned to other client. " +
					"Please, select another username."));
			clientDTO.setMessage(messageDTO);
			userDTO.setSuccess("False");
			clientDTO.setUser(userDTO);
			return ResponseEntity.unprocessableEntity().body(clientDTO);
    	}

        return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);
    }
    
    //Add client without giving user
    @PostMapping("/fastclient") 
    public ResponseEntity<?> addFastClient(@RequestBody ClientDTO client) {
		ClientDTO finalClient;
		MessageDTO messageDTO;

		try {
			finalClient = service.createFastClient(client);
		} catch (Exception e) {
			messageDTO = new MessageDTO("False", e.getMessage());
			return ResponseEntity.unprocessableEntity().body(messageDTO);
		}

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
    
		map.put("total_clients", service.getAllClients().size());
    	map.put("clients_of_the_page", list);
    	return ResponseEntity.ok().body(map);
    }

    //get a client by id 
    @GetMapping("/{id}")   
    public Map<String, Object> getClientById(@PathVariable(name="id") UUID id) {
    	HashMap<String, Object> map = new HashMap<>();
    	try {
    		Client client = service.findClientById(id);
            map.put("success", "true");
            map.put("message", "client found");
            map.put("client", client);
            return map;
    	} catch (Exception e) {
            map.put("success", "false");
            map.put("message", "error: " + e.getMessage());
    	}
    	return map;
    }
       
    //Update a client by id
    @PutMapping()
    public ResponseEntity<?> updateClientById(@RequestBody Client clientUpdate) {

		ClientDTO clientDTO = checkClientErrors(clientUpdate);

		if (null != clientDTO && clientDTO.getMessage().getSuccess().equalsIgnoreCase("False")){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(clientDTO.getMessage());
		}

        try {
			clientDTO = service.updateClient(clientUpdate);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);

    }

	private ClientDTO checkClientErrors(Client client) {
		if (null != client.getDni() && service.existsByDni(client.getDni())) {
			MessageDTO messageDTO = new MessageDTO("False", "Dni already exists");
			ClientDTO clientDTO = new ClientDTO();
			clientDTO.setMessage(messageDTO);
			return clientDTO;
		}
		else if (null != client.getUser() && null != client.getUser().getUsername()
				&& userService.existsByUsername(client.getUser().getUsername())) {
			MessageDTO messageDTO = new MessageDTO("False", "User already assigned to other client. " +
					"Please, select another username.");
			ClientDTO clientDTO = new ClientDTO();
			clientDTO.setMessage(messageDTO);
			return clientDTO;
		}
		return null;
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
