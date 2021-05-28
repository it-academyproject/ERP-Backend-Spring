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
	
	/*
	 * select offers.id, offers.applied, offers.offertype, offers.description, offers.start_date, offers.end_date,
	offers.directdiscount, direct_discount.id, direct_discount.discount,
    offers.freeproducts, freeproducts.id, freeproducts.products_to_buy, freeproducts.products_to_pay
	from offers left join direct_discount 
    on offers.directdiscount = direct_discount.id left join freeproducts on offers.freeproducts = freeproducts.id group by offers.id;

	 * @Query(value = "select "		
			+ "offers.id as id, offers.applied as applied, offers.offertype as offertype, offers.description as description, "
			+ "offers.start_date as start_date, offers.end_date as end_date, offers.directdiscount as id_directdiscount, "
			+ "direct_discount.discount as discount, freeproducts.id as free_products_id, "
			+ "freeproducts.products_to_buy as products_to_buy, freeproducts.products_to_pay as freeproducts.products_to_pay "
			+ "from offers left join direct_discount on offers.directdiscount = direct_discount.id "
			+ "left join freeproducts on offers.freeproducts = freeproducts.id group by offers.id", nativeQuery = true)
	 */

	@Query(value = "select "		
			+ "offers.id, offers.applied, offers.offertype, offers.description, "
			+ "offers.start_date, offers.end_date, offers.directdiscount, "
			+ "direct_discount.discount, freeproducts.id, "
			+ "freeproducts.products_to_buy, freeproducts.products_to_pay "
			+ "from offers left join direct_discount on offers.directdiscount = direct_discount.id "
			+ "left join freeproducts on offers.freeproducts = freeproducts.id group by offers.id", nativeQuery = true)
	List<Object[]> findFullAllOffers();
	
	
}
