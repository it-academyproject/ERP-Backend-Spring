package cat.itacademy.proyectoerp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.dto.ClientDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	IClientRepository repository;

	/*
	 * @Autowired UserRepository repositoryUser;
	 */

	// We use ModelMapper for map Client entity with the DTO.
	ModelMapper modelMapper = new ModelMapper();

	@Override
	public Client createClient(Client client) throws ArgumentNotValidException {
		
		if(client.getid() == null) {
			throw new ArgumentNotValidException("You need to add the UUID manually.For ideas go to: https://www.uuidgenerator.net/ ");
		}
		if(!client.getid().toString().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")){
			throw new ArgumentNotValidException("Invalid UUID");
		}
		if (client.getAddress() == null || client.getAddress().isEmpty()) {
			throw new ArgumentNotValidException("Address can't be empty");
		}
		if (client.getDni() == null || client.getDni().isEmpty()) {
			throw new ArgumentNotValidException("Dni can't be empty");
		} else {
			return repository.save(client);
		}
	}

	@Override
	public List<Client> getAllClients() throws ArgumentNotFoundException {
		if (repository.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No clients found");
		}
		return repository.findAll();
	}

	/**
	 * Method for list all users with DTO format.
	 * 
	 * @return List of all users in DTO FORMAT!!! example.
	 */

	@Override
	public List<ClientDTO> listAllUsers() {

		List<ClientDTO> listaUsers = new ArrayList<>();
		for (Client user : repository.findAll()) {

			ClientDTO cliente = modelMapper.map(user, ClientDTO.class);
			cliente.setUsername(user.getUser().getUsername());

			listaUsers.add(cliente);
		}

		return listaUsers;
	}

	@Override
	public Optional<Client> findClientById(UUID id) throws ArgumentNotFoundException {
		Optional<Client> tempClient = repository.findById(id);
		if (tempClient == null) {
			throw new ArgumentNotFoundException("The client with id " + id + "doesn't exists");
		} else {
			return repository.findById(id);
		}
	}


	@Override
	public void updateClient(Client client) {
		repository.findById(client.getid()).map(clientDB -> {
			BeanUtils.copyProperties(client, clientDB);
			return repository.save(clientDB);
		}).orElseThrow(() -> new ArgumentNotFoundException("Client not found"));
	}

	@Override
	public void deleteClient(UUID id) throws ArgumentNotFoundException {
		if(repository.getOne(id) == null) {
			throw new ArgumentNotFoundException("The client with id " + id + "doesn't exists");
		} else {
			repository.deleteById(id);
		}

	}

}
