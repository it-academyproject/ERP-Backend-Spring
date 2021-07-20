package cat.itacademy.proyectoerp.service;

import java.util.*;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import cat.itacademy.proyectoerp.domain.*;
import cat.itacademy.proyectoerp.dto.*;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	IClientRepository repository;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	UserServiceImpl userService;

	// We use ModelMapper for map Client entity with the DTO.
	ModelMapper modelMapper = new ModelMapper();

	public ClientDTO createClient(Client client) throws Exception {

		String exceptionMessage = "Client not created. ";
		Client clientSaved = saveClientCheckedException(client, exceptionMessage);

		String successMessage = "Client created. ";
		return getSuccesfulClientDTO(clientSaved, successMessage);
	}

	private Client saveClientCheckedException(Client client, String exceptionMessage) throws Exception {
		try {
			return repository.save(client);
		} catch (Exception e) {
			throw new Exception(exceptionMessage);
			//throw new Exception(exceptionMessage.concat(e.getMessage()));
		}
	}

	private ClientDTO getSuccesfulClientDTO(Client client, String message) {
		MessageDTO messageDTO = new MessageDTO("True", message);
		ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
		clientDTO.setMessage(messageDTO);
		return clientDTO;
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
	public Client findByDni(String dni) {
		return repository.findByDni(dni);
	}

	@Override
	public MessageDTO getErrorMessageDniExists(String dni) {
		MessageDTO errorMessage = null;
		if (existsByDni(dni)) {
			errorMessage = new MessageDTO("False", "Dni Exists: '" + dni + "' ");
		}
		return errorMessage;
	}

	@Override
	public List<Client> getAllClients() {
		if (repository.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No clients found. ");
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

		setClient(client, clientById);
		setUserClient(client, clientById);
		setClientAddress(client, clientById);
		setClientShippingAddress(client, clientById);

		String exceptionMessages = getExceptionMessages(client, clientById);

		Client clientUpdated = saveClientCheckedException(client, exceptionMessages);

		String successMessage = "Client updated. ";
		return getSuccesfulClientDTO(clientUpdated, successMessage);
	}

	private void setClient(Client client, Client clientDB) {
		client.setDni(null == client.getDni() ? clientDB.getDni() : client.getDni());
		client.setImage(null == client.getImage() ? clientDB.getImage() : client.getImage());
		client.setNameAndSurname(
				null == client.getNameAndSurname() ? clientDB.getNameAndSurname() : client.getNameAndSurname());
	}

	private void setUserClient(Client client, Client clientDB) {
		if (null != client.getUser()) {
			client.getUser().setId(clientDB.getUser().getId());
			client.getUser().setUsername(null == client.getUser().getUsername() ? clientDB.getUser().getUsername()
					: client.getUser().getUsername());
			client.getUser().setPassword(null == client.getUser().getPassword() ? clientDB.getUser().getPassword()
					: userService.passEconder(client.getUser().getPassword()));
			client.getUser().setUserType(clientDB.getUser().getUserType());
		} else {
			client.setUser(clientDB.getUser());
		}
	}

	private void setClientAddress(Client client, Client clientDB) {
		Address address;
		if (isAddress(client)) {
			// if Address is equals to ShippingAddress,
			// needs a valid Address (new Address)
			Address validAddress = getValidAddress(clientDB);
			clientDB.setAddress(validAddress);
			address = setAddress(client.getAddress(), clientDB.getAddress());
		} else {
			address = clientDB.getAddress();
		}
		client.setAddress(address);
	}

	private boolean isAddress(Client client) {
		Predicate<Client> isAddress = (c) -> (null != c.getAddress());
		return isAddress.test(client);
	}

	private Address getValidAddress(Client clientDB) {
		Address newAddress = clientDB.getAddress();
		if (isShippingAddress(clientDB)
				&& clientDB.getShippingAddress().getId().equals(clientDB.getAddress().getId())) {
			newAddress = new Address(clientDB.getAddress());
		}
		return newAddress;
	}

	private Address setAddress(Address address, Address addressDB) {
		address.setId(addressDB.getId());
		address.setStreet(null == address.getStreet() ? addressDB.getStreet() : address.getStreet());
		address.setNumber(null == address.getNumber() ? addressDB.getNumber() : address.getNumber());
		address.setCity(null == address.getCity() ? addressDB.getCity() : address.getCity());
		address.setCountry(null == address.getCountry() ? addressDB.getCountry() : address.getCountry());
		address.setZipcode(null == address.getZipcode() ? addressDB.getZipcode() : address.getZipcode());
		return address;
	}

	private void setClientShippingAddress(Client client, Client clientDB) {
		Address shippingAddress = null;
		if (isShippingAddress(client, clientDB)) {
			// if ShippingAddress is equals to Address,
			// needs a valid ShippingAddress (new Address)
			Address validShippingAddress = getValidShippingAddress(clientDB);
			clientDB.setShippingAddress(validShippingAddress);
			shippingAddress = setAddress(client.getShippingAddress(), clientDB.getShippingAddress());
		} else if (isShippingAddress(client)) {
			shippingAddress = new Address(client.getShippingAddress());
		} else if (isShippingAddress(clientDB)) {
			shippingAddress = clientDB.getShippingAddress();
		}
		client.setShippingAddress(shippingAddress);
	}

	private boolean isShippingAddress(Client client, Client clientDB) {
		BiPredicate<Client, Client> isShippingAddress = (c,
				cDB) -> (null != c.getShippingAddress() && null != cDB.getShippingAddress());
		return isShippingAddress.test(client, clientDB);
	}

	private boolean isShippingAddress(Client client) {
		Predicate<Client> isShippingAddress = (c) -> (null != c.getShippingAddress());
		return isShippingAddress.test(client);
	}

	private Address getValidShippingAddress(Client clientDB) {
		Address newShippingAddress = clientDB.getShippingAddress();
		if (clientDB.getShippingAddress().getId().equals(clientDB.getAddress().getId())) {
			newShippingAddress = new Address(clientDB.getShippingAddress());
		}
		return newShippingAddress;
	}

	private String getExceptionMessages(Client client, Client clientDB) {
		String exceptionMessage = "Client not updated. ";
		exceptionMessage = exceptionMessage + getExceptionMessageDni(client, clientDB);
		exceptionMessage = exceptionMessage + getExceptionMessageUsername(client);
		exceptionMessage = exceptionMessage + getExceptionMessageShippingAddress(client, clientDB);
		return exceptionMessage;
	}

	private String getExceptionMessageDni(Client client, Client clientDB) {
		String exceptionMessage = "";
		if (null != client.getDni()) {
			Client clientByDni = findByDni(client.getDni());
			if (null != clientByDni && !clientByDni.getId().equals(clientDB.getId())) {
				exceptionMessage = exceptionMessage + "Dni Exists: '" + client.getDni() + "'. ";
			}
		}
		return exceptionMessage;
	}

	private String getExceptionMessageUsername(Client client) {
		String exceptionMessage = "";
		if (null != client.getUser() && null != client.getUser().getUsername()) {
			User userByUsername = userService.findByUsername(client.getUser().getUsername());
			if (null != userByUsername && !userByUsername.getId().equals(client.getUser().getId())) {
				exceptionMessage = exceptionMessage + "User Exists: '" + client.getUser().getUsername() + "'. ";
			}
		}
		return exceptionMessage;
	}

	private String getExceptionMessageShippingAddress(Client client, Client clientDB) {
		String exceptionMessage = "";
		if (isShippingAddress(client) && !isShippingAddress(clientDB)) {
			exceptionMessage = "Because of this client have no any shipping_address assigned,"
					+ " all shipping_address fields are mandatory: " + "street, number, city, country, zipcode. ";
		}
		return exceptionMessage;
	}

	@Override
	public ClientDTO deleteClient(UUID id) {
		ClientDTO clientDTO = modelMapper.map(repository.getOne(id), ClientDTO.class);
		repository.deleteById(id);
		return clientDTO;
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
		for (Client client : clients) {
			ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
			clientsList.add(clientDTO);
		}
		return clientsList;
	}

	@Override
	public ClientDTO createFastClient(ClientDTO client) throws Exception {
		Client finalClient = modelMapper.map(client, Client.class);
		setFastClient(finalClient);

		String exceptionMessage = "Error. Fast client not created. ";
		Client clientSaved = saveClientCheckedException(finalClient, exceptionMessage);

		String successMessage = "Fast client created.";
		return getSuccesfulClientDTO(clientSaved, successMessage);
	}

	private void setFastClient(Client fastClient) {
		Random rand = new Random();
		String random = String.valueOf(rand.nextInt(100000));
		UUID id = UUID.randomUUID();

		fastClient.setId(id);
		fastClient.setDni("A1234" + random + "A");

		setUser(fastClient, random);
		setAddress(fastClient);
	}

	private void setUser(Client fastClient, String randomUsername) {
		String password = userService.passEconder("ReW9a0&+TP");
		User user = new User("userclientfast_" + randomUsername + "@example.com", password, UserType.CLIENT);
		fastClient.setUser(user);
	}

	private void setAddress(Client fastClient) {
		Address fastAddress = new Address("Calle Ejemplo Fast", "1 A", "Barcelona", "Spain", "08018");
		fastClient.setAddress(fastAddress);
		fastClient.setShippingAddress(fastAddress);

	}

}
