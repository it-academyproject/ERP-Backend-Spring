package cat.itacademy.proyectoerp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, UUID> {

	boolean existsByName(String name);

	boolean existsByDescription(String description);
	
	List<Category> findAllByParentCategoryId(UUID id);

}
