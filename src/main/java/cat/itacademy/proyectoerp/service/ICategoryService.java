package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Category;
import cat.itacademy.proyectoerp.dto.CategoryDTO;

public interface ICategoryService {
	
	public CategoryDTO createCategory(CategoryDTO categoryDto);
	
	public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDto);

	public CategoryDTO getCategoryById(UUID id);
		
	public Category findCategoryById(UUID id);
	
	public List<CategoryDTO> getCategories();
	
	public List<CategoryDTO> getCategoriesByParentCategoryName(String name);
	
	public void existsCategoryByName(String name);
	
	public void deleteCategoryById(UUID id);
	
}
