package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Category;
import cat.itacademy.proyectoerp.dto.CategoryDTO;

public interface ICategoryService {
	
	public CategoryDTO createCategory(CategoryDTO categoryDto);
	
	public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDto);
		
	public Category getCategoryById(UUID id);
	
	public List<CategoryDTO> getCategories();
	
	public List<CategoryDTO> getCategoriesByParentCategoryId(UUID id);
	
	public void deleteCategoryById(UUID id);
	
}
