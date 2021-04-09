package cat.itacademy.proyectoerp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.proyectoerp.domain.OrderProduct;

public interface IOrderProductRepository extends JpaRepository <OrderProduct,Integer>{

}
