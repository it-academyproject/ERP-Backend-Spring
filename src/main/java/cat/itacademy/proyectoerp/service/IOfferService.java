package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.OfferDTO;

public interface IOfferService {

	public OfferDTO createOffer(Offer offer);

	public List<OfferDTO> findAllOffers();

	public OfferDTO findOfferById(UUID id);
	
}
