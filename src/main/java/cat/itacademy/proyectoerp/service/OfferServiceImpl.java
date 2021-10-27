package cat.itacademy.proyectoerp.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.ClientDTO;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IOfferRepository;

@Service
public class OfferServiceImpl implements IOfferService {
	
	@Autowired
	private IOfferRepository offerRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<OfferDTO> findAll() throws ArgumentNotFoundException {
		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No offers found");
		
		List<OfferDTO> offerDtos = offerRepository.findAll()
			.stream()
			.map(offer -> modelMapper.map(offer, OfferDTO.class))
			.collect(Collectors.toList());
		
		return offerDtos;
	}
	
	@Override
	public OfferDTO findById(UUID id) throws ArgumentNotFoundException {
		Offer offer = offerRepository.findById(id)
			.orElseThrow(() -> new ArgumentNotFoundException("Offer not found. The id " + id + " doesn't exist"));
		
		return modelMapper.map(offer, OfferDTO.class);
	}
	
	@Override
	public OfferDTO create(Offer offer) {
		offerRepository.save(offer);
		
		return modelMapper.map(offer, OfferDTO.class);
	}
	
	@Override
	public OfferDTO update(Offer offer) throws ArgumentNotFoundException {
		UUID id = offer.getId();
		
		if (!offerRepository.existsById(id))
			throw new ArgumentNotFoundException("Offer not found. The id " + id + " doesn't exist");
		
		offerRepository.save(offer);
		
		return modelMapper.map(offer, OfferDTO.class);
	}
	
		
	@Override
	public OfferDTO delete(UUID id) throws ArgumentNotFoundException {
		
		Offer offer = offerRepository.findById(id).orElseThrow( () -> new ArgumentNotFoundException("Offer not found. The id " + id + " doesn't exist"));
		OfferDTO clientDTO = modelMapper.map(offer, OfferDTO.class);
		offerRepository.deleteById(id);
		return clientDTO;
		
	}

	@Override
	public List<OfferDTO> findByNameContainingIgnoreCase(String name) throws ArgumentNotFoundException {
		
		
		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No offers found");

		List<OfferDTO> offerDtos = offerRepository.findAll()
				.stream().filter(o -> o.getName().toLowerCase().contains(name.toLowerCase()))
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		return offerDtos;
		
	}

	@Override
	public List<OfferDTO> readByDiscountGreatherThanEqual(Double min) {
		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No offers found");

		List<OfferDTO> offerDtos = offerRepository.findAll()
				.stream().filter(o -> o.getDiscount()>=min)
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		return offerDtos;
		
	}

	@Override
	public List<OfferDTO> readByDiscountLessThanEqual(Double max) {
		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No offers found");

		List<OfferDTO> offerDtos = offerRepository.findAll()
				.stream().filter(o -> o.getDiscount()<=max)
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		return offerDtos;
	}

	@Override
	public List<OfferDTO> filterByDiscountBetween(Double min, Double max) {
		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No offers found");

		List<OfferDTO> offerDtos = offerRepository.findAll()
				.stream().filter(o -> o.getDiscount()>=min && o.getDiscount()<=max)
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		return offerDtos;
	}

	@Override
	public List<OfferDTO> filterByStartsOnAfter(String from) {
		
		List<OfferDTO> offerDtosNotNull = new ArrayList<OfferDTO>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDateTime fromDate = LocalDate.parse(from, formatter).atStartOfDay();

		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No offers found");
		
				
		List<OfferDTO> offerDtos = offerRepository.findAll()
				.stream()
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		
				
		
		for (OfferDTO l: offerDtos) {
			
			if (l.getStartsOn()!=null) {
				
				offerDtosNotNull.add(l);
						
			}
		}
		
		//Filer by StartsOnAfter
		
		List<OfferDTO> offerDtosIsAfter = offerDtosNotNull.stream().filter(o -> o .getStartsOn().isAfter(fromDate))
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		
		
		
		return offerDtosIsAfter;
	}

	@Override
	public List<OfferDTO> filterByEndsOnBefore(String to) {
		List<OfferDTO> offerDtosNotNull = new ArrayList<OfferDTO>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDateTime toDate = LocalDate.parse(to, formatter).atStartOfDay();

		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No offers found");
		
				
		List<OfferDTO> offerDtos = offerRepository.findAll()
				.stream()
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		
				
		
		for (OfferDTO l: offerDtos) {
			
			if (l.getEndsOn()!=null) {
				
				offerDtosNotNull.add(l);
						
			}
		}
		
		//Filer by StartsOnAfter
		
		List<OfferDTO> offerDtosIsBefore = offerDtosNotNull.stream().filter(o -> o .getEndsOn().isBefore(toDate))
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		
		
		
		return offerDtosIsBefore;
		
	}

	@Override
	public List<OfferDTO> filterByStartsOnAfterAndEndsOnBefore(String from, String to) {
		List<OfferDTO> offerDtosNotNull = new ArrayList<OfferDTO>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDateTime fromDate = LocalDate.parse(from, formatter).atStartOfDay();
		LocalDateTime toDate = LocalDate.parse(to, formatter).atStartOfDay();

		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No offers found");
		
				
		List<OfferDTO> offerDtos = offerRepository.findAll()
				.stream()
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		
				
		
		for (OfferDTO l: offerDtos) {
			
			if (l.getStartsOn()!=null && l.getEndsOn()!=null) {
				
				offerDtosNotNull.add(l);
						
			}
		}
		
		//Filer by StartsOnAfter & EndsOnBefore
		
		List<OfferDTO> offerDtosisAfterAndIsBefore = offerDtosNotNull.stream()
				.filter(o -> o .getStartsOn().isAfter(fromDate) && o.getEndsOn().isBefore(toDate))
				.map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());
		
		
		
		return offerDtosisAfterAndIsBefore;
		
	}	
}
	

