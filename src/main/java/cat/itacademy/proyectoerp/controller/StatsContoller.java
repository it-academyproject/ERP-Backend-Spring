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
import java.util.UUID;

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
      if(!orderList.isEmpty()){
        map.put("success", "true");
        map.put("message", "order list found");
        map.put("order", orderList);
      } else{
        map.put("success", "true");
        map.put("message", "order list empty");
        map.put("order", orderList);
      }
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", "error: " + e.getMessage());
    }
    return map;
  }

  @GetMapping("/ordersByClient/{id}")
  public Map<String, Object> getOrdersByClient(@PathVariable(value = "id") String id) throws Exception{
    HashMap<String, Object> map = new HashMap<>();

    try {
      List<Order> orderList = orderService.findOrdersByClient(id);
      if(!orderList.isEmpty()){
        map.put("success", "true");
        map.put("message", "order by client list found");
        map.put("order", orderList);
      } else{
        map.put("success", "true");
        map.put("message", "order list by client is empty");
        map.put("order", orderList);
      }
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", "error: " + e.getMessage());
    }
    return map;
  }
}
