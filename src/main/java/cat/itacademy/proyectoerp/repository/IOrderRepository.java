package cat.itacademy.proyectoerp.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;

@Repository
public interface IOrderRepository extends JpaRepository<Order, UUID>{

  List<Order> findByStatus(OrderStatus status);

  List<Order> findByClientId(UUID clientId);  
  
  List<Order> findByEmployeeId(UUID employeeId);
  
  List<Order> findAllByStatus(OrderStatus status);
    
  @Query(value = "select orders.employee_id,sum(orders.total) as total, employees.dni from orders "
		   	+"left join employees on orders.employee_id = employees.id "
                  +"where (orders.status like 'COMPLETED') "	
                  +" and (orders.date_created between :begin_date and :end_date) "
		          +"group by orders.employee_id order by total desc limit 10", nativeQuery = true)
  List<Object[]> findEmployeesSalesBetweenDates(LocalDateTime begin_date, LocalDateTime end_date);
  
  @Query(value = "select sum(total) as total from orders " 
          +"where (status like 'COMPLETED') "	
          +" and (date_created between :begin_date and :end_date) ", nativeQuery = true)
  double findProfitBetweenDates(LocalDateTime begin_date, LocalDateTime end_date);
  
  @Query(value = "select o.status from Order o")
  List<String> findAllOrdersByStatus();
  
  @Query(value = "select o.paymentMethod from Order o")
  List<String> findAllOrdersByPayment();

}
 