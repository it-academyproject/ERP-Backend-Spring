package cat.itacademy.proyectoerp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
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
	public Client createClient(Client client) {
		if(client.getid() == null) {
			throw new ArgumentNotValidException("You need to add the UUID manually.For ideas go to: https://www.uuidgenerator.net/ ");
		}
		else if (client.getAddress() == null || client.getAddress().isEmpty()) {
			throw new ArgumentNotValidException("Address can't be empty");
		}
		else if (client.getDni() == null || client.getDni().isEmpty()) {
			throw new ArgumentNotValidException("Dni can't be empty");
		}
//		else if(repository.existsByDni(client.getDni())) {
//			throw new ArgumentNotValidException("Dni already exists");
//		}
		else if(client.getUser() == null) {
			throw new ArgumentNotValidException("You have to assign this client to an user.");
		} else {
			return repository.save(client);
		}
	}

	@Override
	public List<Client> getAllClients() {
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
		if (repository.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No clients found");
		}
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
			return tempClient;
		}
	}

	@Override
	public Client updateClient(Client client) {
		return repository.findById(client.getid()).map(clientDB -> {
			BeanUtils.copyProperties(client, clientDB);
			return repository.save(clientDB);
		}).orElseThrow(() -> new ArgumentNotFoundException("Client not found"));
	}

	@Override
	public void deleteClient(UUID id) throws ArgumentNotFoundException {
		if (repository.getOne(id) == null) {
			throw new ArgumentNotFoundException("The client with id " + id + "doesn't exists");
		} else {
			repository.deleteById(id);
		}
	}

	@Override
	public List<ClientDTO> getPageOfClients(int page, int amount) {
		Pageable pageable = PageRequest.of(page, amount);
		if (pageable.isUnpaged()) {
			throw new ArgumentNotFoundException("Page Not Found");
		}
		Page<Client> clients = repository.findAll(pageable);
		if (clients.isEmpty()) {
			throw new ArgumentNotFoundException("No clients found");
		}
		List<ClientDTO> clientsList = new ArrayList<>();
		for(Client client : clients) {
			ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
			clientsList.add(clientDTO);
		}
		return clientsList;
	}

	@Override
	public Client createFastClient(ClientDTO client) {
		Client finalClient = modelMapper.map(client, Client.class);
		User user = new User("userclientone@example.com", "ReW9a0&+TP", UserType.CLIENT);
		finalClient.setUser(user);
		return repository.save(finalClient);
	}

}
