package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Client;

public interface IClientService {
	
	public Client createClient(Client client); //CREATE - Adds new client to the Database
	
	public List<Client> getAllClients(); // READ - full list of all clients
	
	public Client findClientById(UUID id); //READ - finds the client by Id
	
	public Client updateClient(Client client); //UPDATE - Updates clients info
	
	public void deleteClient(Client client); //DELETE - deletes client
	

}
