package cat.itacademy.proyectoerp.service;

import java.util.List;

import cat.itacademy.proyectoerp.dto.OfferDTO;

public interface IOfferService {
	public OfferDTO createOffer(OfferDTO OfferDto);
	
	public List<OfferDTO> findAll();

}
