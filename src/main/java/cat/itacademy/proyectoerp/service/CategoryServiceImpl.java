package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
	public List<CategoryDTO> read() {
		List<Category> categories = categoryRepository.findAll();
		
		return categories.stream()
			.collect(Collectors.mapping(category -> modelMapper.map(category, CategoryDTO.class), Collectors.toList()));
	}
	
	@Override
	public CategoryDTO readById(UUID id) {
		Category category = categoryRepository.findById(id)
			.orElseThrow(() -> new ArgumentNotFoundException("The id " + id + " doesn't correspond to any category"));
		
		return modelMapper.map(category, CategoryDTO.class);
	}
	
	public Category findById(UUID id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ArgumentNotFoundException("The id " + id + " doesn't correspond to any category"));
	}
	
	@Override
	public CategoryDTO readByName(String name) {
		this.existsByName(name);
		
		Category category = categoryRepository.findByName(name);
		
		return modelMapper.map(category, CategoryDTO.class);
	}
	
	@Override
	public void existsByName(String name) {
		if (!categoryRepository.existsByName(name))
			throw new ArgumentNotValidException("A category named " + name + " doesn't exist");
	}
	
	@Override
	public CategoryDTO create(CategoryDTO categoryDto) {
		Category category = this.create(categoryDto.getName(), categoryDto.getDescription());
		
		categoryRepository.save(category);
		
		return modelMapper.map(category, CategoryDTO.class);
	}
	
	private Category create(String name, String description) {
		this.checkName(name);
		this.checkDescription(description);
		
		return new Category(name, description);
	}
	
	@Override
	public CategoryDTO update(CategoryDTO categoryDto) {
		Category category = this.findById(categoryDto.getId());
		
		category = this.updateNameIfNameExists(category, categoryDto.getName());
		category = this.updateDescriptionIfDescriptionExists(category, categoryDto.getDescription());
		
		categoryRepository.save(category);
		
		return modelMapper.map(category, CategoryDTO.class);
	}
	
	private Category updateNameIfNameExists(Category category, String name) {
		if (name != null) {
			this.checkName(name);
			category.setName(name);
			return category;
		}
		return category;
	}
	
	private Category updateDescriptionIfDescriptionExists(Category category, String description) {
		if (description != null) {
			this.checkDescription(description);
			category.setDescription(description);
			return category;
		}
		return category;
	}
	
	private void checkName(String name) {
		if (StringUtils.isBlank(name))
			throw new ArgumentNotValidException("Name cannot be null or whitespace");
		
		if (categoryRepository.existsByName(name))
			throw new ArgumentNotValidException("A category named " + name + " already exists");
	}
	
	private void checkDescription(String description) {
		if (StringUtils.isBlank(description) || description.length() < 10 || description.length() > 200)
			throw new ArgumentNotValidException("Description must be between 10 and 200 characters");
		
		if (categoryRepository.existsByDescription(description))
			throw new ArgumentNotValidException("Another category has the same description");
	}
	
	@Override
	public void delete(CategoryDTO categoryDto) {
		UUID id = categoryDto.getId();
		
		this.readById(id);
		
		categoryRepository.deleteById(id);
	}
	
}
