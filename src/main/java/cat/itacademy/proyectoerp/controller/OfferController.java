package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.service.IOfferService;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

	@Autowired
	//private OfferServiceImpl offerService;
	IOfferService offerService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public Map <String, Object> addNewOffer(@RequestBody Offer offer){
		
		HashMap<String, Object> map = new HashMap<>();
	
		try { 
			
			OfferDTO finalOffer = offerService.createOffer(offer);
			map.put("succes","true");
			map.put("message","offer created");
			map.put("offer:", finalOffer);
		
		}catch (Exception e) {
			map.put("success", "false");
		    map.put("message", "_error: " + e.getMessage());
		}
		
		return map;
	}
	
	//Method to return all offers
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map <String, Object> getAllOffer(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<OfferDTO> offersList = offerService.findAllOffers();
			map.put("success", "true");
			map.put("message", "offers found");
			map.put("offers", offersList);
		
		}catch (Exception e) {
			map.put("success", "false");
		    map.put("message", "_error: " + e.getMessage());
		    
		}
		return map;
	
	}

	//Method to return a specific offer	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public Map<String, Object> findOfferById(@PathVariable(name = "id") UUID id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			OfferDTO offerFinded = offerService.findOfferById(id);
			map.put("success", "true");
			map.put("message", "offer found");
			map.put("offer", offerFinded);
		
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "_error " + e.getMessage());
		
		}
		return map;
	}
	
	//Method to return all future offers
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/next", method = RequestMethod.GET)
	public Map <String, Object> getNextAllOffer(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<OfferDTO> offersList = offerService.findAllNextOffers();
			
			map.put("message", "offers found");
			map.put("success", "true");
			map.put("offers", offersList);
		
		}catch (Exception e) {
			map.put("success", "false");
		    map.put("message", "_error: " + e.getMessage());
		    
		}
		return map;
	
	}

	//Method to return all past offers
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/past", method = RequestMethod.GET)
	public Map <String, Object> getPastAllOffer(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<OfferDTO> offersList = offerService.findAllPastOffers();
			
			map.put("message", "offers found");
			map.put("success", "true");
			map.put("offers", offersList);
		
		}catch (Exception e) {
			map.put("success", "false");
		    map.put("message", "_error: " + e.getMessage());
		    
		}
		return map;
	}
	
	//Method to return all current offers
	//@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public Map <String, Object> getCurrenttAllOffer(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<OfferDTO> offersList = offerService.findAllCurrentOffers();
			
			map.put("message", "offers found");
			map.put("success", "true");
			map.put("offers", offersList);
		
		}catch (Exception e) {
			map.put("success", "false");
		    map.put("message", "_error: " + e.getMessage());
		    
		}
		return map;
	}
	
	
	//Method to delete an offer	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public Map<String, Object> deleteOffer(@PathVariable(name = "id") UUID id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			offerService.delOfferById(id);
			map.put("success", "true"); 
			map.put("message", "offer deleted");
		
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "_error " + e.getMessage());
		
		}
		return map;
	}
		
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
		
	public Map<String, Object> updateOffer(@RequestBody Offer offer) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			OfferDTO offerupdated = offerService.updateOffer(offer);
			map.put("success", "true"); 
			map.put("message", "offer updated");
			map.put("offer", offerupdated);
		
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "_error " + e.getMessage());
		
		}
		return map;
	} 


}
