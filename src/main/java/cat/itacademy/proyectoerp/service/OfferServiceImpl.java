package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IOfferRepository;

import org.modelmapper.ModelMapper;

@Service
public class OfferServiceImpl implements IOfferService {

	@Autowired
	IOfferRepository OfferRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public OfferDTO createOffer(OfferDTO OfferDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GetMapping
	public List<OfferDTO> findAll() throws ArgumentNotFoundException {
		
				if(OfferRepository.findAll().isEmpty())
					throw new ArgumentNotFoundException("No Offers found");
				
				List<OfferDTO> OffersDTO = OfferRepository.findAll().stream().map(Offer -> modelMapper.map(Offer, OfferDTO.class)).collect(Collectors.toList());
						
				return OffersDTO;
			
		
	}

}
