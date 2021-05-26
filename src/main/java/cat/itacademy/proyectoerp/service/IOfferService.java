package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.OfferDTO;

public interface IOfferService {

	OfferDTO createOffer(Offer offer); //CREATE - create new offer

	List<OfferDTO> findAllOffers(); // READ - Method to return all offers

	OfferDTO findOfferById(UUID id); // READ - Method to return 1 offer by ID

	List<OfferDTO> findAllNextOffers(); // READ - Method to return all offers that END DATE > NOW DATE

	List<OfferDTO> findAllPastOffers();  // READ - Method to return all offers that END DATE < NOW DATE

	List<OfferDTO> findAllCurrentOffers();  // READ - Method to return all current offers that START DATE < NOW DATE and END DATE > NOW DATE  

	void delOfferById(UUID id);   //DEL - Method to delete an Offer by ID

	OfferDTO updateOffer(Offer offer);//UPDATE - Method to UPDATE an Offer
	
}
