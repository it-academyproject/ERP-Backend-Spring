package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Category;
import cat.itacademy.proyectoerp.dto.CategoryDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.ICategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService {
	
	@Autowired
	ICategoryRepository categoryRepository;
	
	ModelMapper modelMapper = new ModelMapper();

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) {
		Category category;
		if(categoryDto.getParentCategoryId() == null) {
			category = createParentCategory(categoryDto.getName(), categoryDto.getDescription());
		}else {
			category = createSubCategory(categoryDto.getName(), categoryDto.getDescription(), categoryDto.getParentCategoryId());
		}
		categoryRepository.save(category);
		return modelMapper.map(category, CategoryDTO.class);
	}
	
	private Category createParentCategory(String name, String description) {
		checkCategoryName(name);
		checkCategoryDescription(description);
		return new Category(name, description);
	}

	private Category createSubCategory(String name, String description, UUID parentCategoryId) {
		checkCategoryName(name);
		checkCategoryDescription(description);
		Category parentCategory = getCategoryById(parentCategoryId);
		checkParentCategoryIsNotSubCategory(parentCategory);
		return new Category(name, description, parentCategory);
	}

	@Override
	public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDto) {
		Category category = getCategoryById(id);
		category = updateCategoryNameIfNameExists(category, categoryDto.getName());
		category = updateCategoryDescriptionIfDescriptionExists(category, categoryDto.getDescription());
		category = updateCategoryParentCategoryIfParentCategoryExists(category, categoryDto.getParentCategoryId());
		categoryRepository.save(category);
		return modelMapper.map(category, CategoryDTO.class);
	}
	
	private Category updateCategoryNameIfNameExists(Category category, String name) {
		if(name != null) {
			checkCategoryName(name);
			category.setName(name);
			return category;
		}
		return category;
	}

	private Category updateCategoryDescriptionIfDescriptionExists(Category category, String description) {
		if(description != null) {
			checkCategoryDescription(description);
			category.setDescription(description);
			return category;
		}
		return category;
	}

	private Category updateCategoryParentCategoryIfParentCategoryExists(Category category, UUID parentCategoryId) {
		if(parentCategoryId != null) {
			Category parentCategory = getCategoryById(parentCategoryId);
			checkParentCategoryIsNotSubCategory(parentCategory);
			category.setParentCategory(parentCategory);
			return category;
		}
		return category;
	}

	private void checkCategoryName(String name) {
		if(categoryRepository.existsByName(name)) throw new ArgumentNotValidException("A category named " + name + " already exists");		
	}
	
	private void checkCategoryDescription(String description) {
		if(description.isBlank() || description.length() < 10 || description.length() > 200) throw new ArgumentNotValidException("Description must be between 10 and 200 characters");
		if(categoryRepository.existsByDescription(description)) throw new ArgumentNotValidException("Another catergory has the same description");
	}

	private void checkParentCategoryIsNotSubCategory(Category parentCategory) {
		if(parentCategory.getParentCategory() != null) throw new ArgumentNotValidException("A category can only be subcategory of a parent category, not of another subcategory");	
	}

	@Override
	public Category getCategoryById(UUID id) {;
		return categoryRepository.findById(id).orElseThrow(() -> new ArgumentNotFoundException("The id " + id + " doesn't correspond to any category"));
	}

	@Override
	public List<CategoryDTO> getCategories() {
		return categoryRepository.findAll().stream().collect(Collectors.mapping(category -> modelMapper.map(category, CategoryDTO.class), Collectors.toList()));
	}

	@Override
	public List<CategoryDTO> getCategoriesByParentCategoryId(UUID id) {
		return categoryRepository.findAllByParentCategoryId(id).stream().collect(Collectors.mapping(category -> modelMapper.map(category, CategoryDTO.class), Collectors.toList()));
	}

	@Override
	public void deleteCategoryById(UUID id) {
		categoryRepository.deleteById(id);		
	}

}
