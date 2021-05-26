package cat.itacademy.proyectoerp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.proyectoerp.domain.FreeProducts;


public interface IFreeProductRepository extends JpaRepository<FreeProducts, Integer> {

}
