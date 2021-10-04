package cat.itacademy.proyectoerp.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.service.IOfferService;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

	@Autowired
	private IOfferService offerService;

	/**
	 * 
	 * Method for Get all Offers
	 * ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */
		
	@PreAuthorize("hasRole('ADMIN')" +
            " || hasRole('EMPLOYEE')" )
	@GetMapping()
	public MessageDTO getOffers() {
		MessageDTO messageDto;

		try {
			List<OfferDTO> offerDtos = offerService.findAll();

			messageDto = new MessageDTO("true", "Offers found", offerDtos);
		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return messageDto;
	}

	/**
	 * 
	 * Method for read Offers by Id	
	 * ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */
		
	@PreAuthorize("hasRole('ADMIN')" +
            " || hasRole('EMPLOYEE')" )
	
	@GetMapping("/{id}")
	public MessageDTO getOfferById(@PathVariable UUID id) {
		MessageDTO messageDto;

		try {
			
			OfferDTO offerDto = offerService.findById(id);

			messageDto = new MessageDTO("true", "Offer found", offerDto);
		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return messageDto;
	}
	
	
	

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MessageDTO create(@Valid @RequestBody Offer offer) {
		MessageDTO messageDto;

		try {
			OfferDTO offerDto = offerService.create(offer);

			messageDto = new MessageDTO("true", "Offer with id: " + offerDto.getId() + " has been created", offerDto);
		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return messageDto;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public MessageDTO update(@Valid @RequestBody Offer offer) {
		MessageDTO messageDto;

		try {
			OfferDTO offerDto = offerService.update(offer);

			messageDto = new MessageDTO("true", "Offer with id: " + offer.getId() + " has been updated", offerDto);
		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return messageDto;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping
	public MessageDTO delete(@Valid @RequestBody Offer offer) {
		MessageDTO messageDto;

		try {
			offerService.delete(offer);

			messageDto = new MessageDTO("true", "Offer with id: " + offer.getId() + " has been deleted");
		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return messageDto;
	}
	
	/**
	 * 
	 * Method for filter Offers by name 	
	 * ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */
	
	@PreAuthorize("hasRole('ADMIN')" +
            " || hasRole('EMPLOYEE')" )
	
	@GetMapping("/")
	public MessageDTO filterByNameContainingIgnoreCase(@RequestParam (name = "name") String name) {
		MessageDTO messageDto;

		try {
			
			List<OfferDTO> offerDtos = offerService.findByNameContainingIgnoreCase(name);
					

			messageDto = new MessageDTO("true", "Offer found", offerDtos);
		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return messageDto;
	}
	
	/**
	 * 
	 * Method for filter offers by minimum discount 	
	 * ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */
	
	@PreAuthorize("hasRole('ADMIN')" +
            " || hasRole('EMPLOYEE')" )
	
	@GetMapping("/discount")
	public MessageDTO readByDiscountGtratherThanEaual(@RequestParam (name = "min") Double min) {
		MessageDTO messageDto;

		try {
			
			List<OfferDTO> offerDtos = offerService.readByDiscountGtratherThanEaual(min);
					

			messageDto = new MessageDTO("true", "Offer found", offerDtos);
		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return messageDto;
	}

}
