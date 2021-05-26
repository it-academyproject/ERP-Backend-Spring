package cat.itacademy.proyectoerp.controller;

import java.util.*;


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
    public ResponseEntity<?> addNewClient(@Valid @RequestBody Client client) {
		UserDTO userDTO = null;
		ClientDTO clientDTO = new ClientDTO();
		try {
			MessageDTO errorMessage = service.getErrorMessageDniExists(client.getDni());
			if(null != errorMessage){
				return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			userDTO = getUser(client);

			Client newClient = getClient(client, userDTO.getUsername());
			clientDTO = service.createClient(newClient);
		} catch (Exception e) {
			clientDTO.setUser(userDTO);
			return ResponseEntity.unprocessableEntity().body(clientDTO);
		}
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);
    }

	private UserDTO getUser(Client client) {
		UserDTO userDTO;
		if(userService.existsByUsername(client.getUser().getUsername())){
			userDTO = userService.getByUsername(client.getUser().getUsername()).get();
			setErrorMessageUsernameExists(userDTO);
		}
		else{
			userDTO = userService.registerNewUserAccount(client.getUser());
		}
		return userDTO;
	}

	private void setErrorMessageUsernameExists(UserDTO userDTO) {
		MessageDTO errorMessage = new MessageDTO("False", "Username Exists. ");
		userDTO.setSuccess(errorMessage.getSuccess());
		userDTO.setMessage(errorMessage.getMessage());
	}

	private Client getClient(Client client, String username) {
		User user = userService.findByUsername(username);
		Client newClient = new Client(client.getDni(),client.getImage(),client.getNameAndSurname(),
				client.getAddress(),client.getShippingAddress(),user);
		return newClient;
	}

    //Add client without giving user
    @PostMapping("/fastclient") 
    public ResponseEntity<?> addFastClient(@RequestBody ClientDTO client) {
		ClientDTO finalClient;

		MessageDTO messageDTO = getErrorMessageNameAndSurname(client.getNameAndSurname());
		if(null != messageDTO){
			return ResponseEntity.unprocessableEntity().body(messageDTO);
		}

		try {
			finalClient = service.createFastClient(client);
		} catch (Exception e) {
			messageDTO = new MessageDTO("False", e.getMessage());
			return ResponseEntity.unprocessableEntity().body(messageDTO);
		}
    	return ResponseEntity.ok().body(finalClient);
    }

	private MessageDTO getErrorMessageNameAndSurname(String nameAndSurname) {
		MessageDTO errorMessage = null;
		if (null == nameAndSurname || nameAndSurname.isBlank()){
			errorMessage = new MessageDTO("False", "name_and_surname is mandatory. ");

		}
		return errorMessage;
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
		ClientDTO clientDTO;
		try {
			clientDTO = service.updateClient(clientUpdate);
		} catch (Exception e) {
			MessageDTO messageDTO = new MessageDTO("False", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageDTO);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);
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
