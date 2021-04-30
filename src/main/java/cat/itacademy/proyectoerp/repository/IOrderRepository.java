package cat.itacademy.proyectoerp.repository;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.domain.TopEmployee;
import cat.itacademy.proyectoerp.dto.TopEmployeeDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, UUID>{

  List<Order> findOrdersByStatus(OrderStatus status);

  List<Order> findOrdersByClientId(String clientId);  
  
  List<Order> findOrdersByEmployeeId(String employeeId);
  
  /*@Query(value = "select orders.employee_id as employee_id, "
  		+ "sum(orders.total) as total_sales from orders group by orders.employee_id ", nativeQuery = true)*/ //ok

 // @Query(value = "select new cat.itacademy.proyectoerp.dto.EmployeeDTO(o.employee_id, "
		/*@Query(value = "select new cat.itacademy.proyectoerp.domain.TopEmployee(employee_id, "
  		+ "sum(o.total)) from orders as o group by o.employee_id", nativeQuery = true)  */
  
  @Query(value = "select employee_id,sum(total) as total from orders group by employee_id order by total desc limit 10", nativeQuery = true)
		//+ "where (orders.status like 'COMPLETED') "  
  List<Object[]> findEmployeesSalesBetweenDates();
  



}
 