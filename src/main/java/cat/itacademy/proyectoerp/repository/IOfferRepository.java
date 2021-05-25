package cat.itacademy.proyectoerp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Offer;

@Repository
public interface IOfferRepository extends JpaRepository<Offer, UUID> {

	@Query(value = "select * from offers where offers.start_date > :date "
	       +"order by start_date asc", nativeQuery = true)
	List<Offer> findNextOffers(LocalDateTime date);

	
	@Query(value = "select * from offers where offers.end_date < :date "
		       +"order by start_date asc", nativeQuery = true)
	List<Offer> findPastOffers(LocalDateTime date);


	@Query(value = "select * from offers where offers.start_date <= :date and offers.end_date > :date "
		       +"order by start_date asc", nativeQuery = true)
	List<Offer> findCurrentOffers(LocalDateTime date);

}
