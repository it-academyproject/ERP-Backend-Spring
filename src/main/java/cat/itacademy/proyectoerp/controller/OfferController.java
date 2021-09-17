package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.service.IOfferService;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
	
	@Autowired
	IOfferService offerService ;
	
	@GetMapping
	public HashMap<String, Object> getOffers() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<OfferDTO> OfferList = offerService.findAll();
			
			map.put("success", "true");
			map.put("message", "Offers found");
			map.put("Offers", OfferList);
			
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public Map<String, Object> getOfferById(@PathVariable(name="id") UUID id){
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			OfferDTO offerDTO = offerService.findOfferById(id);
			map.put("success", "true");
			map.put("message", "offer found");
			map.put("offer", offerDTO);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> create(@Valid @RequestBody Offer offer) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			OfferDTO offerDto = offerService.create(offer);
			
			map.put("success", "true");
			map.put("message", "Offer with id: " + offerDto.getId() + " has been created");
			map.put("offer", offerDto);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public Map<String, Object> update(@Valid @RequestBody Offer offer) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			OfferDTO offerDto = offerService.update(offer);
			
			map.put("success", "true");
			map.put("message", "Offer with id: " + offerDto.getId() + " has been updated");
			map.put("offer", offerDto);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping
	public Map<String, Object> delete(@Valid @RequestBody Offer offer) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			offerService.delete(offer);
			
			map.put("success", "true");
			map.put("message", "Offer with id: " + offer.getId() + " has been deleted");
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
}
