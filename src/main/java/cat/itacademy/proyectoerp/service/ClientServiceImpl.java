package cat.itacademy.proyectoerp.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.*;
import cat.itacademy.proyectoerp.dto.*;
import cat.itacademy.proyectoerp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	IClientRepository repository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserServiceImpl userService;

	/*
	 * @Autowired UserRepository repositoryUser;
	 */

	// We use ModelMapper for map Client entity with the DTO.
	ModelMapper modelMapper = new ModelMapper();

	@Override
	public ClientDTO createClient(Client client) throws Exception {
		Client clientSaved;

		try {
			 clientSaved = repository.save(client);
		}catch (Exception e){
			throw new Exception("Client not created. ");
		}
		MessageDTO messageDTO = new MessageDTO("True","Client created.");
		ClientDTO clientDTO = modelMapper.map(clientSaved, ClientDTO.class);
		clientDTO.setMessage(messageDTO);
		return clientDTO;
	}

	@Override
	public boolean existsByUsername(String username) {
		boolean existsByUsername = false;
		if (repository.existsByDni(username)) {
			existsByUsername = true;
		}
		return existsByUsername;
	}

	@Override
	public boolean existsByDni(String dni) {
		boolean existsByDni = false;
		if (repository.existsByDni(dni)) {
			existsByDni = true;
		}
		return existsByDni;
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
	public Client findClientById(UUID id) throws ArgumentNotFoundException {
		return repository.findById(id)
	            .orElseThrow(() -> new ArgumentNotFoundException("Client not found. The id " + id + " doesn't exist"));
	  }	 
	  
	@Override
	public ClientDTO updateClient(Client client) throws Exception {
		Client clientById = repository.findById(client.getId()).orElseThrow(
				() -> new ArgumentNotFoundException("Client not found. The id " + client.getId() + " doesn't exist"));

		validateClient(client, clientById);
		validateUserClient(client, clientById);
		validateClientAddress(client, clientById);
		validateClientShippingAddress(client, clientById);

		Client clientUpdated;
		try {
			clientUpdated = repository.save(client);
		}catch (Exception e){
			throw new Exception("Client not updated. There is no shipping_address assigned to this client." +
					" Please, check that all shipping_address fields are informed");
		}

		MessageDTO messageDTO = new MessageDTO("True","Client updated.");
		ClientDTO clientDTO = modelMapper.map(clientUpdated, ClientDTO.class);
		clientDTO.setMessage(messageDTO);
		return clientDTO;
	}

	private void validateClient(Client client, Client clientDB) {
		client.setDni(null == client.getDni()? clientDB.getDni(): client.getDni());
		client.setImage(null == client.getImage()? clientDB.getImage(): client.getImage());
		client.setNameAndSurname(null == client.getNameAndSurname()?clientDB.getNameAndSurname():client.getNameAndSurname());
	}

	private void validateUserClient(Client client, Client clientDB) {
		if(client.getUser() != null){
			client.getUser().setId(clientDB.getUser().getId());
			client.getUser().setUsername(null == client.getUser().getUsername()? clientDB.getUser().getUsername():
					client.getUser().getUsername());
			client.getUser().setPassword(null == client.getUser().getPassword()? clientDB.getUser().getPassword():
					userService.passEconder(client.getUser().getPassword()));
			client.getUser().setUserType(clientDB.getUser().getUserType());
		}else{
			client.setUser(clientDB.getUser());
		}
	}

	private void validateClientAddress(Client client, Client clientDB) {
		Address addressValidated;
		if(null != client.getAddress()){
			Address address = client.getAddress();
			Address addressDB = clientDB.getAddress();
			addressValidated =  validateAddress(address, addressDB);
		} else{
			addressValidated = clientDB.getAddress();
		}
		client.setAddress(addressValidated);
	}

	private void validateClientShippingAddress(Client client, Client clientDB) {
		Address shippingAddressValidated = null;
		if(null != client.getShippingAddress() && null != clientDB.getShippingAddress()){
			Address shippingAddress = client.getShippingAddress();
			Address shippingAddressDB = clientDB.getShippingAddress();
			shippingAddressValidated =  validateAddress(shippingAddress, shippingAddressDB);
		}
		else if (null != clientDB.getShippingAddress() && null == client.getShippingAddress()){
			shippingAddressValidated = clientDB.getShippingAddress();
		}
		else if (null == clientDB.getShippingAddress() && null != client.getShippingAddress()){
			shippingAddressValidated = new Address(client.getShippingAddress().getStreet(),
					client.getShippingAddress().getNumber(), client.getShippingAddress().getCity(),
					client.getShippingAddress().getCountry(), client.getShippingAddress().getZipcode());
		}
		client.setShippingAddress(shippingAddressValidated);
	}

	private Address validateAddress(Address address, Address addressDB) {
		address.setId(addressDB.getId());
		address.setStreet(null == address.getStreet()? addressDB.getStreet():
				address.getStreet());
		address.setNumber(null == address.getNumber()? addressDB.getNumber():
				address.getNumber());
		address.setCity(null == address.getCity()? addressDB.getCity():
				address.getCity());
		address.setCountry(null == address.getCountry()? addressDB.getCountry():
				address.getCountry());
		address.setZipcode(null == address.getZipcode()? addressDB.getZipcode():
				address.getZipcode());
		return address;
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
	public ClientDTO createFastClient(ClientDTO client) throws Exception {
		Client clientSaved;

		Random rand = new Random();
		String random = String.valueOf(rand.nextInt(100000));

		UUID id = UUID.randomUUID();

		String password = userService.passEconder("ReW9a0&+TP");

		Client finalClient = modelMapper.map(client, Client.class);
		finalClient.setId(id);
		finalClient.setDni("A1234"+random+"A");
		User user = new User("userclientfast_"+random+"@example.com", password, UserType.CLIENT);
		finalClient.setUser(user);
		Address fastAddress = new Address("Calle Ejemplo Fast", "1 A", "Barcelona", "Spain", "08018");
		finalClient.setAddress(fastAddress);
		finalClient.setShippingAddress(fastAddress);
		try {
			clientSaved = repository.save(finalClient);
		}catch (Exception e){
			//throw new Exception("Client not created. User not created.");
			throw new Exception(e.getMessage());
		}
		MessageDTO messageDTO = new MessageDTO("True","Fast client created.");
		modelMapper.map(clientSaved.getAddress(), AddressDTO.class);
		modelMapper.map(clientSaved.getShippingAddress(), AddressDTO.class);
		ClientDTO clientDTO = modelMapper.map(clientSaved, ClientDTO.class);
		clientDTO.setMessage(messageDTO);
		return clientDTO;
	}

}
