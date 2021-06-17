package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.domain.Shop;
import cat.itacademy.proyectoerp.dto.ShopDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IShopRepository;

@Service
public class ShopServiceImpl implements IShopService {

	@Autowired
	IShopRepository shopRepo;

	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	@Transactional
	public ShopDTO createShop(Shop shop) throws ArgumentNotValidException {
		CheckIfShopBrandNameExist(shop);
		shop = shopRepo.save(shop);
		return ConvertShopToDTO(shop);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShopDTO> getShops() throws ArgumentNotFoundException {
		// if there are no shops, throw exception
		if (shopRepo.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No shops found");
		} else {
			List<ShopDTO> shopDTOList = shopRepo.findAll().stream().map(s->ConvertShopToDTO(s)).collect(Collectors.toList());		
			return shopDTOList;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ShopDTO findShopById(UUID id) throws ArgumentNotFoundException {
		Shop shop = GetShopByIdFromRepository(id);
		return ConvertShopToDTO(shop);
	}

	@Override
	@Transactional
	public ShopDTO updateShop(Shop shop) throws ArgumentNotValidException, ArgumentNotFoundException {

		//check if the new brand change and if the new one is available
		Shop shopPrevious = GetShopByIdFromRepository(shop.getId());
		if(!shopPrevious.getBrandName().equals(shop.getBrandName())) CheckIfShopBrandNameExist(shop);
		
		//keep creation date
		shop.setCreationDate(shopPrevious.getCreationDate());
		
		//update shop
		shop = shopRepo.save(shop);
		return ConvertShopToDTO(shop);
	}

	@Override
	@Transactional
	public void deleteShop(UUID id) {
		shopRepo.deleteById(id);
	}
		
	private ShopDTO ConvertShopToDTO(Shop shop) {
		return modelMapper.map(shop,ShopDTO.class);
	}
	
	private void CheckIfShopBrandNameExist(Shop shop) {
		if(shopRepo.existsByBrandName(shop.getBrandName())) throw new EntityExistsException("The brand name: " + shop.getBrandName() + " is already in use");
	}
	
	private Shop GetShopByIdFromRepository(UUID id) {
		//if there is no shop with that id throw exception
		Shop shop = shopRepo.findById(id)
				.orElseThrow(() -> new ArgumentNotFoundException("Shop not found. The id: " + id + " doesn't exist"));
		return shop;
	}

}
