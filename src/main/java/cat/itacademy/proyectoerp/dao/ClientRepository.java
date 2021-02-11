package cat.itacademy.proyectoerp.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.proyectoerp.domain.Client;

public interface ClientRepository extends JpaRepository<Client,UUID> {

}
