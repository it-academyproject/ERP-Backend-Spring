package cat.itacademy.proyectoerp.repository;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, UUID>{

  List<Order> findOrdersByStatus(OrderStatus status);

  List<Order> findOrdersByClientId(String clientId);
  
  List<Order> findOrdersByEmployeeId(String employeeId);
  
  List<Order> findAllByStatus(OrderStatus status);
}
