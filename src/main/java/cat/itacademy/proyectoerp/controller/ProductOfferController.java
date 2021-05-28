package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.dto.OfferFullDTO;
import cat.itacademy.proyectoerp.dto.OfferProductDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IOfferRepository;
import cat.itacademy.proyectoerp.repository.IProductRepository;
import cat.itacademy.proyectoerp.service.IProductOfferService;

@RestController
@RequestMapping("/api/applyoffer/")
public class ProductOfferController {
	@Autowired
	IProductOfferService productOfferService;
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	IOfferRepository offerRepository;
	
	
	//Metodo no finalizado ni chequeado pdte documentar.
	@PostMapping
    public HashMap<String, Object> addNewProductInOffer(@RequestBody OfferProductDTO offerproductpojo) throws MethodArgumentTypeMismatchException{
		HashMap<String, Object> map = new HashMap<>();
		try {
			productOfferService.createProductOffer(offerproductpojo);
			map.put("success", "true");
			map.put("message", "prodcuts & offers created");
			map.put("products_offers", offerproductpojo);
		 
		}catch (Exception e) {
			map.put("success", "false");
		    map.put("message", "_error: " + e.getMessage());
		    
		}
		return map;

    }
	

}
