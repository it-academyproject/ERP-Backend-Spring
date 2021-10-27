package cat.itacademy.proyectoerp.service;


import java.util.List;
import java.util.UUID;
import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.OfferDTO;

public interface IOfferService {
	
	public List<OfferDTO> findAll();
	
	public OfferDTO findById(UUID id);
			
	public OfferDTO create(Offer offer);
	
	public OfferDTO update(Offer offer);
	
	public OfferDTO delete(UUID id);
	
		
	public List<OfferDTO> findByNameContainingIgnoreCase(String name);
	
	public List<OfferDTO> readByDiscountGreatherThanEqual(Double min);
	
	public List<OfferDTO> readByDiscountLessThanEqual(Double max);
	
	public List<OfferDTO> filterByDiscountBetween(Double min,Double max);
	
	public List<OfferDTO> filterByStartsOnAfter(String from);
	
	public List<OfferDTO> filterByEndsOnBefore(String to);
	
	public List<OfferDTO> filterByStartsOnAfterAndEndsOnBefore(String from,String to);
	
}
