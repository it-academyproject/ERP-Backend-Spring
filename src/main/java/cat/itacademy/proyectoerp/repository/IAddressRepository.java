package cat.itacademy.proyectoerp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Address;

@Repository
public interface IAddressRepository extends JpaRepository <Address, UUID> {

}
