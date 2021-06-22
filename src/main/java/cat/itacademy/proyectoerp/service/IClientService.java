package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.dto.ClientDTO;
import cat.itacademy.proyectoerp.dto.MessageDTO;

public interface IClientService {
	
	public static ClientDTO createClient(Client client) throws Exception {
	
		return null;
	} //CREATE - Adds new client to the Database
	
	public ClientDTO createFastClient(Client client) throws Exception; // CREATE - Adds a new client without needing an username. Only for admin purposes.
	
	public List<Client> getAllClients(); // READ - full list of all clients
	
	public List<ClientDTO> getPageOfClients(int page, int amount); // READ - Get a page of X clients
	
	public Client findClientById(UUID id); //READ - finds the client by Id
		
	public ClientDTO updateClient(Client client) throws Exception; //UPDATE - Updates clients info
	
	public void deleteClient(UUID id); //DELETE - deletes client

	List<ClientDTO> listAllUsers();

	Client findByDni(String dni);

	boolean existsByDni(String dni);

	MessageDTO getErrorMessageDniExists(String dni);

}
