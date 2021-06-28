package cat.itacademy.proyectoerp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, UUID>{

  List<Order> findOrdersByStatus(OrderStatus status);

  List<Order> findOrdersByClientId(String clientId);  
  
  List<Order> findByEmployeeId(UUID employeeId);
  
  List<Order> findAllByStatus(OrderStatus status);
    
  @Query(value = "select employee_id,sum(total) as total from orders " 
                  +"where (orders.status like 'COMPLETED') "	
                  +" and (orders.date_created between :begin_date and :end_date) "
		          +"group by employee_id order by total desc limit 10", nativeQuery = true)
  List<Object[]> findEmployeesSalesBetweenDates(LocalDateTime begin_date, LocalDateTime end_date);
  
  @Query(value = "select sum(total) as total from orders " 
          +"where (orders.status like 'COMPLETED') "	
          +" and (orders.date_created between :begin_date and :end_date) ", nativeQuery = true)
  double findProfitBetweenDates(LocalDateTime begin_date, LocalDateTime end_date);

}
 