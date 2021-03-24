package cat.itacademy.proyectoerp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.domain.OrderDetailPK;


public interface IOrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK>{

}
