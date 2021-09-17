package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.dto.CategoryDTO;

public interface ICategoryService {
	
	public List<CategoryDTO> read();
	
	public CategoryDTO readById(UUID id);
	
	public CategoryDTO readByName(String name);
	
	public void existsByName(String name);
	
	public CategoryDTO create(CategoryDTO categoryDto);
	
	public CategoryDTO update(CategoryDTO categoryDto);
	
	public void delete(CategoryDTO categoryDto);
	
}
