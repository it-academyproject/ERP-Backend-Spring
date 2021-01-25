package cat.itacademy.proyectoerp.services;

import java.util.List;
import java.util.Optional;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.dto.UserDTO;

/**
 * Interface for User Service.
 * @author Rubén Rodríguez
 *
 */
public interface IUserService {

	public Optional<UserDTO> getByUsername(String username);
	public UserDTO registerNewUserAccount(UserDTO userDto);
	public List<UserDTO> listAllUsers();
	public List<UserDTO> listAllEmployees();
	public List<UserDTO> listAllClients();
	public Optional<UserDTO> deleteUserById(Long id);
	public Optional<UserDTO> modifyUser(Long id, UserDTO userDto);




}
