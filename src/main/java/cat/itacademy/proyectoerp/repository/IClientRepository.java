package cat.itacademy.proyectoerp.repository;



import java.util.UUID;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.User;

@Repository
public interface IClientRepository extends JpaRepository<Client,UUID> {
	

	/**
	 * Method for search a dni
	 * @return boolean if dni exists.
	 */
	boolean existsByDni(String dni);
	
	
	/**
	 * Method for search user by dni.
	 * @param dni
	 * @return Client if client exist or null.
	 */
	Client findByDni(String dni);

}
