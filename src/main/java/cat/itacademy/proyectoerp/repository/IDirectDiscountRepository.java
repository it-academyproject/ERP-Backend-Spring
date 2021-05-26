package cat.itacademy.proyectoerp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.DirectDiscount;


@Repository
public interface IDirectDiscountRepository extends JpaRepository<DirectDiscount,Integer>{

}
