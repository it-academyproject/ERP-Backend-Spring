package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.Optional;
import cat.itacademy.proyectoerp.domain.ChangeUserPassword;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;

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
	public User updatePassword(ChangeUserPassword changeuserpassword);
	public User findByUsername(String username);
	boolean existsByUsername(String username);
	MessageDTO getErrorMessageUsernameExists(String username);
	public void updateLastSession(String username);
	public String handlePasswordFail(String username);

}
