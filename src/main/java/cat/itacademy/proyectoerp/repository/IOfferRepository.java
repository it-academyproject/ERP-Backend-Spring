package cat.itacademy.proyectoerp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import cat.itacademy.proyectoerp.domain.Offer;


public interface IOfferRepository  extends JpaRepository<Offer, UUID> {

}
