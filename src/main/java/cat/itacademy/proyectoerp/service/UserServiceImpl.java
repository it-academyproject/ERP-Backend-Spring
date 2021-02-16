package cat.itacademy.proyectoerp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.*;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import cat.itacademy.proyectoerp.security.dao.IPasswordTokenDAO;
import cat.itacademy.proyectoerp.security.entity.PasswordResetToken;
import cat.itacademy.proyectoerp.util.PasswordGenerator;

/**
 * Class Service for User entity
 * 
 * @author ITAcademy 
 *
 */
@Transactional
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IPasswordTokenDAO passwordTokenDao;

	// We use ModelMapper for map User entity with the DTO.
	ModelMapper modelMapper = new ModelMapper();

	/**
	 * Method for search a entity by username.
	 * 
	 * @param username username to Search
	 * @return Optional<UserDTO> a userDTO object
	 */
	public Optional<UserDTO> getByUsername(String username) {
		UserDTO userDTO = new UserDTO();

		// Verify if user Exist.
		User user = userRepository.withUsername(username);
		if (user == null) {
			userDTO.setSuccess("Failed");
			userDTO.setMessage("User don't exist");
			return Optional.of(userDTO);
		}

		userDTO = modelMapper.map(user, UserDTO.class);
		userDTO.setMessage("User found");
		userDTO.setSuccess("Success");
		return Optional.of(userDTO);

	}

	/**
	 * Method for create a new user. If user
	 * 
	 * @param userDto
	 * @return UserDTO
	 * 
	 */
	@Override
	public UserDTO registerNewUserAccount(User user) {

		modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
				.setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
		// The default typeUser is CLIENT. If client don't send typeUSer, back assign
		// CLIENT how userType.
		if (user.getUserType() == null)
			user.setUserType(UserType.CLIENT);

		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		System.out.println("user pasado " + user.toString());
		if (userRepository.existsWithUsername(user.getUsername())) {
			userDto.setSuccess("False");
			userDto.setMessage("User Exist");
			return userDto;
		}

		user.setPassword(passEconder(user.getPassword()));
		// user = modelMapper.map(userDto, User.class);
		userRepository.save(user);
		userDto.setSuccess("True");
		userDto.setMessage("User created");
		return userDto;

	}

	public String passEconder(String pass) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(pass);
		System.out.println("pass encrip " + encodedPassword);
		return encodedPassword;
	}

	/**
	 * Method for list all users.
	 * 
	 * @return List of all users.
	 */
	@Override
	public List<UserDTO> listAllUsers() {

		List<UserDTO> listaUsers = new ArrayList<>();

		for (User user : userRepository.findAll()) {
			listaUsers.add(modelMapper.map(user, UserDTO.class));

		}

		return listaUsers;
	}

	/**
	 * Method for list all Employee users.
	 * 
	 * @return list of all Employee users.
	 * 
	 */
	@Override
	public List<UserDTO> listAllEmployees() {
		List<UserDTO> listaUsers = new ArrayList<UserDTO>();

		for (User user : userRepository.withUserType(UserType.EMPLOYEE)) {
			listaUsers.add(modelMapper.map(user, UserDTO.class));

		}

		return listaUsers;
	}

	/**
	 * Method for list all Client users.
	 * 
	 * @return list of all Cloient users.
	 * 
	 */
	@Override
	public List<UserDTO> listAllClients() {
		List<UserDTO> listaUsers = new ArrayList<UserDTO>();

		for (User user : userRepository.withUserType(UserType.CLIENT)) {
			listaUsers.add(modelMapper.map(user, UserDTO.class));

		}

		return listaUsers;
	}

	/**
	 * Method for delete user by id.
	 * 
	 * @param id id of user to delete
	 */
	@Override
	@Transactional
	public Optional<UserDTO> deleteUserById(Long id) {
		UserDTO userDto = new UserDTO();
		// Verify if id exist
		if (!userRepository.existsById(id)) {
			userDto = new UserDTO();
			userDto.setSuccess("False");
			userDto.setMessage("User Don't Exist");
			return Optional.of(userDto);
		}

		userRepository.deleteById(id);

		userDto.setSuccess("True");
		userDto.setMessage("User Deleted");
		return Optional.of(userDto);
	}

	/**
	 * Method for modify data of User.
	 * 
	 * @param id      id of user to modify.
	 * @param userDto user data to modify.
	 */
	@Transactional
	@Override
	public Optional<UserDTO> modifyUser(Long id, User user) {
		// UserDTO don't have a password property.
		// For this reason we need save password in a variable for add to user object.
		// Obviously, if password have been send.
		String password = null;

		if (user.getPassword() != null)
			password = user.getPassword();

		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		// Verify if user id exist
		if (!userRepository.existsById(id)) {
			userDto.setSuccess("False");
			userDto.setMessage("User Don't Exist");
			return Optional.of(userDto);
		}
		// Verify if username already exist
		if (userRepository.withUsername(user.getUsername()) != null) {
			userDto.setSuccess("Failed");
			userDto.setMessage("User already exist");
			return Optional.of(userDto);
		}

		user = userRepository.findById(id).get();

		/**
		 * We Verified what properties has received.
		 */

		if (userDto.getUsername() != null)
			user.setUsername(userDto.getUsername());
		if (password != null)
			user.setPassword(passEconder(password));
		if (userDto.getUserType() != null)
			user.setUserType(userDto.getUserType());

		userRepository.save(user);
		userDto.setSuccess("True");
		userDto.setMessage("User modified");
		return Optional.of(userDto);

	}

	/**
	 * Method to recover password
	 * 
	 * @param username
	 * @return new password
	 * @throws ArgumentNotValidException if username does not exist
	 */
	public String recoverPassword(String username) throws ArgumentNotValidException {

		User user = userRepository.withUsername(username);

		// Verify if username exists
		if (user == null) {
			throw new ArgumentNotValidException("The username does not exist");
		}

		// Generate random password
		PasswordGenerator pass = new PasswordGenerator();

		String password = pass.generatePassword();

		// set user password (encrypted)
		user.setPassword(passEconder(password));

		// Save user new password in ddbb
		userRepository.save(user);

		return password;

	}

	/**
	 * Method to create a new token
	 * 
	 * @param user
	 * @param token
	 */
	public void createPasswordResetTokenForUser(User user, String token) {

		PasswordResetToken newToken = new PasswordResetToken(token, user);
		// Save token to database
		passwordTokenDao.save(newToken);
	}

	/**
	 * Method to find user by username
	 * 
	 * @param username
	 * @return user
	 * @throws ArgumentNotFoundException
	 */
	@Override
	public User findUserByUsername(String username) throws ArgumentNotFoundException {
		
		// Verify if user Exist.
		User userFound = userRepository.withUsername(username);
		if (userFound == null) {
			throw new ArgumentNotFoundException("No username found");
		}

		return userFound;
	}

	/**
	 * Method to update user password
	 * 
	 * @param user
	 * @return user with new password
	 * @throws ArgumentNotFoundException
	 */
	@Override
	public User updatePassword(User user) throws ArgumentNotFoundException {

		// Verify if user Exist.
		User updatedUser = userRepository.withUsername(user.getUsername());

		if (updatedUser != null) {
			updatedUser.setPassword(passEconder(user.getPassword()));

			return userRepository.save(updatedUser);

		} else {
			throw new ArgumentNotFoundException("No username found");
		}
	}
}
