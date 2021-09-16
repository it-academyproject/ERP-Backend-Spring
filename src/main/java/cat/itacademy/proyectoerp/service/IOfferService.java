package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.dto.OfferDTO;

public interface IOfferService {
	
	public List<OfferDTO> findAll();
	
	public OfferDTO findOfferById(UUID id);
	
	public OfferDTO create(OfferDTO offerDto);
	
	public OfferDTO update(OfferDTO offerDto);
	
	public void delete(OfferDTO offerDto);
	
}
