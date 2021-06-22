package cat.itacademy.proyectoerp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import cat.itacademy.proyectoerp.dto.MessageDTO;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.*;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.UserRepository;

import cat.itacademy.proyectoerp.util.PasswordGenerator;

/**
 * Class Service for User entity
 * 
 * @author ITAcademy
 **/
@Transactional
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailServiceImpl emailService;

	// We use ModelMapper for map User entity with the DTO.
	ModelMapper modelMapper = new ModelMapper();

	/**
	 * Method to search an entity by username.
	 * 
	 * @param username username to Search
	 * @return Optional<UserDTO> a userDTO object
	 */

	public Optional<UserDTO> getByUsername(String username) {
		UserDTO userDTO = new UserDTO();

		// Verify if user Exist.
		User user = userRepository.findByUsername(username);
		if (user == null) {
			userDTO.setSuccess("False");
			userDTO.setMessage("User don't exist");
			return Optional.of(userDTO);
		}

		userDTO = modelMapper.map(user, UserDTO.class);
		userDTO.setMessage("User found");
		userDTO.setSuccess("True");
		return Optional.of(userDTO);

	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean existsByUsername(String username) {
		boolean existsByUsername = false;
		if (userRepository.existsByUsername(username)) {
			existsByUsername = true;
		}
		return existsByUsername;
	}

	@Override
	public MessageDTO getErrorMessageUsernameExists(String username) {
		MessageDTO errorMessage=null;
		if(existsByUsername(username)){
			errorMessage = new MessageDTO("False", "Username Exists: '"+ username +"'");
		}
		return errorMessage;
	}

	/**
	 * Method for create a new user. If user
	 * 
	 * @param user
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

		if (userRepository.existsByUsername(user.getUsername())) {
			userDto.setSuccess("False");
			userDto.setMessage("User Exist");
			return userDto;
		}

		user.setPassword(passEconder(user.getPassword()));
		// user = modelMapper.map(userDto, User.class);
		userRepository.save(user);

		// send welcome email to new user
		try {
			emailService.sendWelcomeEmail(user);
		} catch (MailException e) {
			userDto.setSuccess("True");
			userDto.setMessage("User created. But the mail could not be sent.");

			return userDto;
		}

		userDto.setSuccess("True");
		userDto.setMessage("User created and Welcome email sended.");
		return userDto;

	}

	public String passEconder(String pass) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(pass);
		//System.out.println("pass encrip " + encodedPassword);
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

		for (User user : userRepository.findByUserType(UserType.EMPLOYEE)) {
			listaUsers.add(modelMapper.map(user, UserDTO.class));

		}

		return listaUsers;
	}

	/**
	 * Method for list all Client users with DTO format.
	 * 
	 * @return list of all Client users.
	 * 
	 */
	@Override
	public List<UserDTO> listAllClients() {
		List<UserDTO> userList = new ArrayList<UserDTO>();

		for (User user : userRepository.findByUserType(UserType.CLIENT)) {
			userList.add(modelMapper.map(user, UserDTO.class));

		}

		return userList;
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
	 * Method to set data of User.
	 * 
	 * @param id      id of user to modify.
	 * @param user user data to modify.
	 */
	@Transactional
	@Override
	public Optional<UserDTO> setUser(Long id, User user) {
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
		if (userRepository.findByUsername(user.getUsername()) != null) {
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
	

		userRepository.save(user);
		userDto.setSuccess("True");
		userDto.setMessage("User modified");
		return Optional.of(userDto);

	} 
	
	/**
	 * Method to set active field of user to "false".
	 *
	 * @param user user data to modify.
	 */	
	@Override
	public UserDTO setSubscription(@Valid User user) {
		
		UserDTO userDto = verifyUser(user);
		
		if (!(userDto.getSuccess()=="False")) {
		
			userDto = verifySubscription(user);
		}
	
		if (!(userDto.getSuccess()=="False")) {
			emailService.sendFarewellEmail(user);
			user.setUserType(userRepository.findById(user.getId()).get().getUserType());
			user.setPassword(userRepository.findById(user.getId()).get().getPassword());
			user.setActive(false);
			userRepository.save(user);
			userDto.setMessage("User is now unsubscribed");
			return userDto;
		}
		
		return userDto;
	}	
	
	public UserDTO verifyUser (User user) {
		
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		
		if (!userRepository.existsById(user.getId())) {
			userDto.setSuccess("False");
			userDto.setMessage("User doesn't exist");
			return userDto;
		}
		
		// Verifies if username matches
		if (!(userDto.getSuccess()=="False")) {
			
			String usernamerepo = userRepository.findById(user.getId()).get().getUsername();
			
			if ( !usernamerepo.equals(user.getUsername())) {
				userDto.setSuccess("False");
				userDto.setMessage("The username does not match, please check it again");
				return userDto;
			}	
		}
		
		userDto.setSuccess("True");
		return userDto;
	}
	
	public UserDTO verifySubscription (User user) {
		
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		
		if (userRepository.findById(user.getId()).get().getActive() == false) {
			userDto.setSuccess("False");
			userDto.setMessage("User is already unsubscribed");
			return userDto;			
		}
		
		userDto.setSuccess("True");		
		return userDto;
	}

	/**
	 * Method to recover password. Generate a new password and send it by email to
	 * the user
	 * 
	 * @param username
	 * @return new password
	 * @throws ArgumentNotFoundException
	 * @throws MailException
	 */
	public String recoverPassword(String username) throws ArgumentNotFoundException, MailException {

		User user = userRepository.findByUsername(username);

		// Verify if username exists
		if (user == null) {
			throw new ArgumentNotFoundException("username not found");
		}

		// Generate random password
		PasswordGenerator pass = new PasswordGenerator();

		String password = pass.generatePassword();

		// send the new random password to the user's email
		emailService.sendPasswordEmail(user, password);

		// set user password (encrypted)
		user.setPassword(passEconder(password));

		// Save user new password in ddbb
		userRepository.save(user);

		return password;
	}

	/**
	 * Method to update user password
	 * 
	 * @param changeuserpassword
	 * @return user with new password
	 * @throws ArgumentNotFoundException
	 */
	@Override
	public User updatePassword(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		ChangeUserPassword changeUserPassword = new ChangeUserPassword(user);

		// Verify if user exist
		User updatedUser = userRepository.findByUsername(user.getUsername());
		
		if (updatedUser != null) {
			//Check password
			if (passwordEncoder.matches(user.getPassword(), updatedUser.getPassword())) {

				// set user password (encrypted)
				
				updatedUser.setPassword(passEconder(changeUserPassword.getNew_password()));
	
				return userRepository.save(updatedUser);
			} else {
				throw new ArgumentNotFoundException("wrong password");
			}
		} else {
			throw new ArgumentNotFoundException("username not found");
		}
	}

	/**
	 * 
	 * Method to update User lastSession with the current LocalDateTime.
	 * 
	 * @param username
	 */
	public void updateLastSession(String username) {
		User user = userRepository.findByUsername(username);
		user.setLastSession(LocalDateTime.now());
	}
}