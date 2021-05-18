package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.domain.Order;
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
	
	//Methos to return all offers
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map <String, Object> getAllOffer(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<OfferDTO> offersList = service.findAllOffers();
			map.put("success", "true");
			map.put("message", "order found");
			map.put("order", offersList);
		
		}catch (Exception e) {
			map.put("success", "false");
		    map.put("message", "_error: " + e.getMessage());
		    
		}
		
		return map;
		
		
	}
	
	/*
	 * @GetMapping("/orders")
	public HashMap<String, Object> findOrders() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Order> ordersList = orderService.findAllOrders();
		try {
			map.put("success", "true");
			map.put("message", "order found");
			map.put("order", ordersList);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}
		return map;
	}
	 * 
	 */
	
	
	
	
	

}
