package cat.itacademy.proyectoerp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.domain.ProductOffer;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.dto.OfferProductDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IOfferRepository;
import cat.itacademy.proyectoerp.repository.IProductOfferRepository;
import cat.itacademy.proyectoerp.repository.IProductRepository;

@Service
public class ProductOfferServiceImpl implements IProductOfferService{
	@Autowired
	IProductOfferRepository productOfferRepository;
	
	@Autowired
	IOfferRepository offerRepository;
	
	@Autowired
	IProductRepository productRepository;
	

	//Not terminated to review
	@Override
	public void createProductOffer(OfferProductDTO offerproductdto) {
		
		Offer offer = offerRepository.findById(offerproductdto.getOffer_id())
				.orElseThrow(() -> new ArgumentNotFoundException("Offer not found. The id " + offerproductdto.getOffer_id() + " doesn't exist"));
		
		for (int p : offerproductdto.getProductList()) {
			Product product = productRepository.findById(p)
					.orElseThrow(() -> new ArgumentNotFoundException("Product not found. The id " + p + " doesn't exist"));
			
			ProductOffer productoffer = new ProductOffer (product, offer);
			productOfferRepository.save(productoffer);
			
		}
		
	}

}
