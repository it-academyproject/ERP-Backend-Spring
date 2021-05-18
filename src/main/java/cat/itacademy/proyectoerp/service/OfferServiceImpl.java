package cat.itacademy.proyectoerp.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.DirectDiscount;
import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.domain.OfferApplied;
import cat.itacademy.proyectoerp.domain.OfferType;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IOfferRepository;

@Service
public class OfferServiceImpl implements IOfferService{
	
	@Autowired
	IOfferRepository offerRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	
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
		}
		offerRepository.save(offer);
		
		OfferDTO offerDto = modelMapper.map(offer, OfferDTO.class);

		return offerDto;
	
	}

}
