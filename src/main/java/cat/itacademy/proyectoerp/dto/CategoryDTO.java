package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID id, parentCategoryId;
	private String name, parentCategoryName, description;
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setParentCategoryId(UUID parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public UUID getParentCategoryId() {
		return parentCategoryId;
	}
	
	public String getParentCategoryName() {
		return parentCategoryName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentCategoryId == null) ? 0 : parentCategoryId.hashCode());
		result = prime * result + ((parentCategoryName == null) ? 0 : parentCategoryName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryDTO other = (CategoryDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentCategoryId == null) {
			if (other.parentCategoryId != null)
				return false;
		} else if (!parentCategoryId.equals(other.parentCategoryId))
			return false;
		if (parentCategoryName == null) {
			if (other.parentCategoryName != null)
				return false;
		} else if (!parentCategoryName.equals(other.parentCategoryName))
			return false;
		return true;
	}
	
}
