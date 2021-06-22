package cat.itacademy.proyectoerp.domain;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import javax.persistence.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "category_id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(unique = true)
	@NotBlank(message = "Name cannot be null or whitespace")
	private String name;
	
	@Column(unique = true)
	@NotBlank(message = "Description cannot be null or whitespace")
	@Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
	private String description;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Product> products;
	
	@ManyToOne
	private Category parentCategory;
	
	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.REMOVE)
	private Set<Category> subCategories;
	
	public Category() {
		
	}
	
	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Category(String name, String description, Category parentCategory) {
		this.name = name;
		this.description = description;
		this.parentCategory = parentCategory;
	}

	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

}
