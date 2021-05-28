package cat.itacademy.proyectoerp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.ProductOffer;


@Repository
public interface IProductOfferRepository extends JpaRepository<ProductOffer, UUID>{

}
