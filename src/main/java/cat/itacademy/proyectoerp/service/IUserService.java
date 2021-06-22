package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import cat.itacademy.proyectoerp.domain.ChangeUserPassword;
import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;
import org.springframework.security.core.Authentication;

/**
 * Interface for User Service.
 * @author Rubén Rodríguez
 *
 */
public interface IUserService {

	public Optional<UserDTO> getByUsername(String username);
	public UserDTO registerNewUserAccount(User user);
	public List<UserDTO> listAllUsers();
	public List<UserDTO> listAllEmployees();
	public List<UserDTO> listAllClients();
	public Optional<UserDTO> deleteUserById(Long id);
	public Optional<UserDTO> setUser(Long id, User user);
	public UserDTO setSubscription (User user);
	public User updatePassword(ChangeUserPassword changeUserPassword);
	public User findByUsername(String username);
	public User findById(Long id);
	boolean existsByUsername(String username);
	MessageDTO getErrorMessageUsernameExists(String username);
	public void updateLastSession(String username);
	public String passEncoder(String password);

}
