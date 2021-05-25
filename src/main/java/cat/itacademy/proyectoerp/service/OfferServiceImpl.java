package cat.itacademy.proyectoerp.service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.domain.DirectDiscount;
import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.domain.OfferApplied;
import cat.itacademy.proyectoerp.domain.OfferType;
import cat.itacademy.proyectoerp.dto.OfferDTO;

import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IOfferRepository;

@Service
//@Transactional
//@ComponentScan({"cat.itacademy.proyectoerp.repository"})
public class OfferServiceImpl implements IOfferService{
	
	@Autowired
	IOfferRepository offerRepository;
	
	@Override
	@Transactional
	public OfferDTO createOffer(Offer offer) {  
		
		if ((offer.getFreeproducts() != 0) && (offer.getDirectdiscount() !=0)){

			throw new ArgumentNotValidException("Fields directdiscount and freeproducts cannot filled");
		 
		}else if ((offer.getFreeproducts() == 0) && (offer.getDirectdiscount() == 0)) {
		
			throw new ArgumentNotValidException("The directdiscount and freeproducts fields cannot be concurrent null");
		
		} else if ((offer.getDescription() == "") || (offer.getDescription() == null)) {
			
			throw new ArgumentNotValidException("The offer must have a description");
		
		}else if (offer.getStartDate().isAfter(offer.getEndDate())) {

			throw new ArgumentNotValidException("The start_date cannot be later than the End_date.");
				
		}else if (offer.getEndDate().isBefore(LocalDateTime.now())) {
			
			throw new ArgumentNotValidException("The end date cannot be earlier than the current date.");
			
		}else if (offer.getStartDate().isBefore(LocalDateTime.now())) {
			
			throw new ArgumentNotValidException("The begin date cannot be earlier than the current date.");
		
		}else if ((offer.getOffertype().name() == "DIRECT_DISCOUNT") && (offer.getFreeproducts() != 0) && 
				(offer.getDirectdiscount() ==0)){
			
			throw new ArgumentNotValidException("Types offer and application no compatible.");
 
		}else if ((offer.getOffertype().name() == "FREE_PRODUCTS") && (offer.getFreeproducts() == 0) && 
				(offer.getDirectdiscount() !=0)){
			
			throw new ArgumentNotValidException("Types offer and application no compatible.");	
		}
		offerRepository.save(offer);
		
		OfferDTO offerDto = new OfferDTO(offer);

		return offerDto;
	
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<OfferDTO> findAllOffers() {
		List <Offer> offerlist =  offerRepository.findAll();
		if (offerlist.isEmpty()) {
			throw new ArgumentNotFoundException("No offers found");
		}else {
			
			List<OfferDTO> offerlistDTO = new ArrayList<OfferDTO>(offerToOfferDTO(offerlist));
			return offerlistDTO;
		}
	}
	

	@Override
	@Transactional(readOnly = true)
	public OfferDTO findOfferById(UUID id) {
		OfferDTO offerdto = new OfferDTO(offerRepository.findById(id)
				.orElseThrow(() -> new ArgumentNotFoundException("Offer not found. The id " + id + " doesn't exist")));
		
		return offerdto;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<OfferDTO> findAllNextOffers() {
		List <Offer> offerlist =  offerRepository.findNextOffers(LocalDateTime.now());
		if (offerlist.isEmpty()) {
			throw new ArgumentNotFoundException("No next offers found");
		}else {
			
			List<OfferDTO> offerlistDTO = new ArrayList<OfferDTO>(offerToOfferDTO(offerlist));

			return offerlistDTO;
		}
	}


	@Override
	@Transactional(readOnly = true)
	public List<OfferDTO> findAllPastOffers() {
		List <Offer> offerlist =  offerRepository.findPastOffers(LocalDateTime.now());
		if (offerlist.isEmpty()) {
			throw new ArgumentNotFoundException("No past offers found");
		}else {
			
			List<OfferDTO> offerlistDTO = new ArrayList<OfferDTO>(offerToOfferDTO(offerlist));

			return offerlistDTO;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OfferDTO> findAllCurrentOffers() {
		List <Offer> offerlist =  offerRepository.findCurrentOffers(LocalDateTime.now());
		if (offerlist.isEmpty()) {
			throw new ArgumentNotFoundException("No current offers found");
		}else {
			
			List<OfferDTO> offerlistDTO = new ArrayList<OfferDTO>(offerToOfferDTO(offerlist));

			return offerlistDTO;
		}
	}
	
	
	@Override
	public void delOfferById(UUID id) {
		Offer offertodelete = offerRepository.findById(id)
					.orElseThrow(() -> new ArgumentNotFoundException("Offer not found. The id " + id + " doesn't exist"));

		if (offertodelete.getStartDate().isAfter(LocalDateTime.now())) {
			offerRepository.delete(offertodelete); 
			
		} else {
			throw new ArgumentNotFoundException("You can't delete this offer because the offer has expired o is currently.");

		}
		
		
		/*COMENTARIOS -> pendiente revisar y borrar productos afectados*/
	}
	
	
	

	//Auxiliar Methods
	public List<OfferDTO> offerToOfferDTO(List <Offer> offerlist){
		List<OfferDTO> offerlistDTO = new ArrayList<OfferDTO>();
		for (Offer o : offerlist) {
			offerlistDTO.add(new OfferDTO(o));
		}
		return offerlistDTO;
		
	}


	
	
}
