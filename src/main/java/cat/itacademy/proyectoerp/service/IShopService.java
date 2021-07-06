package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Shop;
import cat.itacademy.proyectoerp.dto.ShopDTO;

public interface IShopService {

	public ShopDTO createShop(Shop shop); // CREATE - create new shop

	public List<ShopDTO> getShops(); // READ - read shop list data

	public ShopDTO findShopById(UUID id); // READ - read data of a shop according to id

	public ShopDTO updateShop(Shop shop); // UPDATE - update shop data

	public void deleteShop(UUID id); // DELETE - delete shop according to id

}
