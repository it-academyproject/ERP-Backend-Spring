package cat.itacademy.proyectoerp.dao;



import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,UUID> {

}
