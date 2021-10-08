package cat.itacademy.proyectoerp.repository;


import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Offer;


@Repository
public interface IOfferRepository  extends JpaRepository<Offer, UUID> {

	
	
}
