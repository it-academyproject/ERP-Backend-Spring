package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IOfferRepository;

import org.modelmapper.ModelMapper;

@Service
public class OfferServiceImpl implements IOfferService {
	
	@Autowired
	IOfferRepository offerRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Override	
	public List<OfferDTO> findAll() throws ArgumentNotFoundException {
		if(offerRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No Offers found");
		
		List<OfferDTO> OffersDTO = offerRepository.findAll().stream().map(Offer -> modelMapper.map(Offer, OfferDTO.class)).collect(Collectors.toList());
		
		return OffersDTO;
	}
	
	@Override
	public OfferDTO findOfferById(UUID id) throws ArgumentNotFoundException {
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
	public OfferDTO update(Offer offer) {
		UUID id = offer.getId();
		
		if (!offerRepository.existsById(id))
			throw new ArgumentNotFoundException("Offer not found. The id " + id + " doesn't exist");
		
		offerRepository.save(offer);
		
		return modelMapper.map(offer, OfferDTO.class);
	}
	
	@Override
	public void delete(Offer offer) {
		UUID id = offer.getId();
		
		if (!offerRepository.existsById(id))
			throw new ArgumentNotFoundException("Offer not found. The id " + id + " doesn't exist");
	
		offerRepository.deleteById(id);
	}	
	
}
