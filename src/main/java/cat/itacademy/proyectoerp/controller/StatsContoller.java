package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.service.IOrderService;
import cat.itacademy.proyectoerp.util.StringToOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsContoller {

  @Autowired
  IOrderService orderService;

  @GetMapping("/status/{status}")
  public Map<String, Object> getOrderBySatus(@PathVariable(value = "status") String status) throws Exception{
    HashMap<String, Object> map = new HashMap<>();
    try {
      List<Order> orderList = orderService.findOrdersByStatus(StringToOrderStatus.stringToOrderStatus(status));
      map.put("success", "true");
      map.put("message", "order list found");
      map.put("order", orderList);
    } catch (Exception e) {

      map.put("success", "false");
      map.put("message", "error: " + e.getMessage());
    }
    return map;
  }
}
