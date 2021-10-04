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
	
	public void delete(Offer offer);
	
	//public OfferDTO findByName(String name);
	
	public List<OfferDTO> findByNameContainingIgnoreCase(String name);
	
}
