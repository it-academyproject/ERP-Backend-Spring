package cat.itacademy.proyectoerp.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import cat.itacademy.proyectoerp.domain.OrderDetail;

@Validated
public interface IOrderDetailService {
	
	OrderDetail create(@NotNull(message="The products for order cannot be null") @Valid OrderDetail orderDetail);

}
