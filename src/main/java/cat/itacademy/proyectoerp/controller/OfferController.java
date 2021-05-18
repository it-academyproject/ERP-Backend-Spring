package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.service.IOfferService;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
	
	@Autowired
	IOfferService service;
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public Map <String, Object> addNewOffer(@RequestBody Offer offer){
		
		HashMap<String, Object> map = new HashMap<>();
		
	
		try { 
			
			OfferDTO finalOffer = service.createOffer(offer);
			
			
			map.put("succes","true");
			map.put("message","offer created");
			
			map.put("offer:", finalOffer);
		
		}catch (Exception e) {
			map.put("success", "false");
		    map.put("message", "_error: " + e.getMessage());
		}
		
		return map;
	}
	
	
	

}
