package cat.itacademy.proyectoerp.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.fasterxml.jackson.databind.node.ObjectNode;

import cat.itacademy.proyectoerp.domain.Client;
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
	 * Method for Get all Offers ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */

	@PreAuthorize("hasRole('ADMIN')" + " || hasRole('EMPLOYEE')")
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
	 * Method for read Offers by Id ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */

	@PreAuthorize("hasRole('ADMIN')" + " || hasRole('EMPLOYEE')")

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
	
	public Map<String, Object> deleteOffer(@RequestBody ObjectNode objectNode) {
		UUID id = UUID.fromString(objectNode.get("id").asText());
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			offerService.deleteOffer(id);
			map.put("success", true);
			map.put("message", "Offer with id: " + id + " has been deleted");
		} catch (Exception e) {
			map.put("success..", false);
			map.put("message", e.getMessage());
		}
		
		return map;
	}
	
	
	
	/*public ResponseEntity<MessageDTO> deleteOfferyId(@RequestBody Offer offer) {
		MessageDTO output;
		try {
			// empty id case
			if (offer.getId() == null) {
				output = new MessageDTO("False", "empty mandatory field in body request");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
			}
			String name = offerService.deleteOffer(offer.getId()).getDescription();
			output = new MessageDTO("true", "Offer " + name + " has been successfully deleted");
			return ResponseEntity.status(HttpStatus.OK).body(output);
		} catch (Exception e) {
			output = new MessageDTO("False", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(output);
		}
	}*/
	
	
	

	/**
	 * 
	 * Method for filter Offers by name ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */

	@PreAuthorize("hasRole('ADMIN')" + " || hasRole('EMPLOYEE')")

	@GetMapping("/")
	public MessageDTO filterByNameContainingIgnoreCase(@RequestParam(name = "name") String name) {
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
	 * Method for filter offers by discount
	 * ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */

	@PreAuthorize("hasRole('ADMIN')" + " || hasRole('EMPLOYEE')")

	@GetMapping("/discount")
	public MessageDTO readByDiscount(@RequestParam(required = false) Double min,
										@RequestParam(required = false) Double max) {
		MessageDTO messageDto;
		List<OfferDTO> offerDtos = null;
		
		try {

			// min discount
			
			if (min != null) {

				offerDtos = offerService.readByDiscountGreatherThanEqual(min);

			// max discount
				
			}if (max != null) {

				offerDtos = offerService.readByDiscountLessThanEqual(max);

			// discount between min & max
				
			}if (min != null && max != null) {
				
				offerDtos = offerService.filterByDiscountBetween(min, max);
			} 
			
				messageDto = new MessageDTO("true", "Offer found", offerDtos);
			

		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return messageDto;
	}

	/**
	 * 
	 * Method for filter offers by date
	 * ONLY AUTHORIZED TO ADMIN AND EMPLOYEE
	 */

	@PreAuthorize("hasRole('ADMIN')" + " || hasRole('EMPLOYEE')")

	@GetMapping("/date")
	public ResponseEntity<MessageDTO> readByDate(@RequestParam(required = false) String from,
			@RequestParam(required = false) String to){
	
	
		MessageDTO messageDto;
		List<OfferDTO> offerDtos = null;
		
		try {

			
			
			// StartsOn
			
			if (from != null) {

				offerDtos = offerService.filterByStartsOnAfter(from);

			// EndsOn
				
			}if (to != null) {

				offerDtos = offerService.filterByEndsOnBefore(to);

			// StartsOn && EndsOn
				
			}if (from != null && to != null) {
				
				offerDtos =offerService.filterByStartsOnAfterAndEndsOnBefore(from, to);
			} 
			
				messageDto = new MessageDTO("true", "Offer found", offerDtos);
			

		} catch (Exception e) {
			messageDto = new MessageDTO("false", "error: " + e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
	}
	
}
