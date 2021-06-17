package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Shop;
import cat.itacademy.proyectoerp.dto.ShopDTO;
import cat.itacademy.proyectoerp.service.IShopService;

@RestController
@RequestMapping("/api")
public class ShopController {
	
	@Autowired
	IShopService shopService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/shop")
	public HashMap<String, Object> createShop(@Valid @RequestBody Shop shop) {

		HashMap<String, Object> map = new LinkedHashMap<String, Object>();

		try {
			shopService.createShop(shop);

			map.put("success", "true");
			map.put("message", "New shop created");
			map.put("shop", shop);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}
		return map;
	}

	@GetMapping("/shop/{id}")
	public HashMap<String, Object> findShopById(@PathVariable(name = "id") UUID id) {

		HashMap<String, Object> map = new LinkedHashMap<String, Object>();

		try {
			ShopDTO shop = shopService.findShopById(id);

			map.put("success", "true");
			map.put("message", "shop found");
			map.put("shop", shop);

		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}

	@GetMapping("/shops")
	public HashMap<String, Object> getShops() {

		HashMap<String, Object> map = new LinkedHashMap<String, Object>();

		try {
			List<ShopDTO> shopList = shopService.getShops();

			map.put("success", "true");
			map.put("message", "shop list found");
			map.put("shops", shopList);

		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}
		return map;
	}

	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/shop")
	public HashMap<String, Object> updateShop(@Valid @RequestBody Shop shop) {

		HashMap<String, Object> map = new LinkedHashMap<String, Object>();

		try {
			ShopDTO shopUpdated = shopService.updateShop(shop);

			map.put("success", "true");
			map.put("message", "shop updated");
			map.put("shop", shopUpdated);
		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/shop")
	public HashMap<String, Object> deleteShop(@RequestBody ShopDTO shop) {
		
		HashMap<String, Object> map = new LinkedHashMap<String, Object>();

		try {
			shopService.findShopById(shop.getId());
			shopService.deleteShop(shop.getId());

			map.put("success", "true");
			map.put("message", "Shop id: " + shop.getId() + " has been successfully deleted");
		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}
		return map;
	}
}
