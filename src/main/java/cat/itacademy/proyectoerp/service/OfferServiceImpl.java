package cat.itacademy.proyectoerp.service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.domain.DirectDiscount;
import cat.itacademy.proyectoerp.domain.FreeProducts;
import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.domain.OfferApplied;
import cat.itacademy.proyectoerp.domain.OfferType;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.dto.FreeProductDTO;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.dto.OfferFullDTO;
import cat.itacademy.proyectoerp.dto.TopEmployeeDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IDirectDiscountRepository;
import cat.itacademy.proyectoerp.repository.IFreeProductRepository;
import cat.itacademy.proyectoerp.repository.IOfferRepository;

@Service
public class OfferServiceImpl implements IOfferService{
	
	@Autowired
	IOfferRepository offerRepository;
	
	@Autowired
	IFreeProductRepository freeproductRepository;
	
	@Autowired
	IDirectDiscountRepository directdiscountRepository;
	
	
	@Override
	@Transactional
	public OfferDTO createOffer(Offer offer) { 
		
		if (checkOfferIsOk(offer)) {

			offerRepository.save(offer);
		}
		OfferDTO offerDto = new OfferDTO(offer);

		return offerDto;
	
	}

	///////////////////////////////////////////////////////////////////////////////
	@Override
	public List<OfferFullDTO> findFullAllOffers() {
		List<Object[]> offerob =  offerRepository.findFullAllOffers();
		System.out.println("hola");
		List<OfferFullDTO> offerlist = new ArrayList<OfferFullDTO>();
		for (Object[] object : offerob) {
			OfferFullDTO fulloffer = new OfferFullDTO((UUID)object[0],
									(String)object[1],
									(LocalDateTime ) object[2],
									(LocalDateTime ) object[3],
									(String)object[4],
									(String)object[5],
									(Integer)object[6],
									(Double)object[6],
									(Integer)object[7],
									(Integer)object[8],
									(Integer)object[9]);	

			offerlist.add(fulloffer);
		}		
		
		return offerlist;
	}

	
	//////////////////////////////////
	
	
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
			
		/*COMENTARIOS internos @Dapser75 -> No IMPLEMENTADA pendiente revisar.  
		 * No IMPLEMENTADA relación oferta y producto 25/05/21*/
	}
	
	
	@Override
	public OfferDTO updateOffer(Offer offertoupdate) {

		Offer offer = offerRepository.findById(offertoupdate.getId())
				.orElseThrow(() -> new ArgumentNotFoundException("Offer not found. The id " + offertoupdate.getId() + " doesn't exist"));

		//	check dates 
		if ((offer.getStartDate().isAfter(LocalDateTime.now())) 
				&& (offer.getEndDate().isAfter(LocalDateTime.now()))){
			
			//check if all parametres to update are ok
			if (checkOfferIsOk(offertoupdate)) {
				
				//Pdte agrupar estos if en un metodo externo para limpiar codigo
				if ((offertoupdate.getDescription() != "") || (offertoupdate.getDescription() != null))
					offer.setDescription(offertoupdate.getDescription());
				
				if ((offertoupdate.getApplied().toString() != "") || (offertoupdate.getApplied().toString() != null))
					offer.setApplied(offertoupdate.getApplied());
				
				if ((offertoupdate.getOffertype().toString() != "") || (offertoupdate.getOffertype().toString() != null))	
					offer.setOffertype(offertoupdate.getOffertype());
				
				if (offertoupdate.getDirectdiscount() != 0)
					offer.setDirectdiscount(offertoupdate.getDirectdiscount());
				
				if (offertoupdate.getFreeproducts() != 0)
					offer.setFreeproducts(offertoupdate.getFreeproducts());
				
				if (offertoupdate.getStartDate() != null)	
					offer.setStartDate(offertoupdate.getStartDate());
				
				if (offertoupdate.getEndDate() != null)
					offer.setEndDate(offertoupdate.getEndDate());
								
				offerRepository.save(offer);
				
				/*COMENTARIOS internos @Dapser75 -> No IMPLEMENTADA pendiente revisar y 
				 * borrar productos afectados.  No IMPLEMENTADA relación oferta y producto 25/05/21*/
				
			}
	
		} else {
			throw new ArgumentNotFoundException("You can't update this offer because the offer has expired o is currently.");
		}		
		
		return new OfferDTO(offer);
		
	}
	
	//Method to create a new FreeProduct
	@Override
	public FreeProductDTO createFreeProduct(FreeProducts freeproduct) {
		
		if (freeproduct.getProducts_to_buy() <= freeproduct.getProducts_to_pay()) 
			throw new ArgumentNotValidException("Products to buy needs >= Products to pay");
		
		else if (freeproduct.getProducts_to_pay() <= 0)
			throw new ArgumentNotValidException("Products to pay can't be 0");
		
		else if (freeproduct.getProducts_to_buy() <= 0)
			throw new ArgumentNotValidException("Products to buy can't be 0");
					
		else {
			freeproductRepository.save(freeproduct);
		
		}
				
		return new FreeProductDTO(freeproduct);
	}
	
	//Method to get  all FreeProduct
	@Override
	public List <FreeProductDTO> findAllFreeProduct() {
		List <FreeProducts> freeproductslist =  freeproductRepository.findAll();
		if (freeproductslist.isEmpty()) {
			throw new ArgumentNotFoundException("No free product found");
		}else {
			List<FreeProductDTO> freeproductlistDTO = new ArrayList<FreeProductDTO>(freeProductTofreeProductDTO(freeproductslist));
			return freeproductlistDTO;
		}
	}

	@Override
	public FreeProductDTO findFreeProductById(int id) {
		FreeProductDTO freeproductdto = new FreeProductDTO(freeproductRepository.findById(id)
				.orElseThrow(() -> new ArgumentNotFoundException("FreeProduct not found. The id " + id + " doesn't exist")));
	
		return freeproductdto;
	}
	
	
	//-----------------------------------  Complementary Methods  ----------------------------------------
	
	//Method to convert list OFFER to DTO
	public List<OfferDTO> offerToOfferDTO(List <Offer> offerlist){
		List<OfferDTO> offerlistDTO = new ArrayList<OfferDTO>();
		for (Offer o : offerlist) {
			offerlistDTO.add(new OfferDTO(o));
		}
		return offerlistDTO;
		
	}
	
	//Method to convert list FREE PRODUCT to DTO
		public List<FreeProductDTO> freeProductTofreeProductDTO(List <FreeProducts> freeproductlist){
			List<FreeProductDTO> freeproductlistDTO = new ArrayList<FreeProductDTO>();
			for (FreeProducts fp : freeproductlist) {
				freeproductlistDTO.add(new FreeProductDTO(fp));
			}
			return freeproductlistDTO;
			
		}

	//Method to control all parameters in a new/update offer are OK.
	private boolean checkOfferIsOk(Offer offer) {
		
		if ((offer.getFreeproducts() != 0) && (offer.getDirectdiscount() !=0)){

			throw new ArgumentNotValidException("Fields directdiscount and freeproducts cannot filled");
		 
		}else if ((offer.getFreeproducts() == 0) && (offer.getDirectdiscount() == 0)) {
		
			throw new ArgumentNotValidException("The directdiscount and freeproducts fields cannot be concurrent null");
		
		} else if ((offer.getDescription() == "") || (offer.getDescription() == null)) {
			
			throw new ArgumentNotValidException("The offer must have a description");
		
		}else if (offer.getStartDate().isAfter(offer.getEndDate())) {

			throw new ArgumentNotValidException("The Start_date cannot be later than the End_date.");
				
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
		
		}else if ((!freeproductRepository.existsById(offer.getFreeproducts()) &&
				(offer.getFreeproducts() !=0))){
			
			throw new ArgumentNotValidException("This free product not exist.");
			
		}else if ((!directdiscountRepository.existsById(offer.getDirectdiscount()) &&
				(offer.getDirectdiscount() !=0))){
			
			throw new ArgumentNotValidException("This free product not exist.");
			
		}
		else return true;
	}


	
	
}
